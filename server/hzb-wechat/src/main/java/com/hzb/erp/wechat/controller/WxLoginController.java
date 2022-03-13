package com.hzb.erp.wechat.controller;

import com.hzb.erp.common.configuration.SystemConfig;
import com.hzb.erp.common.configuration.WxMpProperties;
import com.hzb.erp.common.constants.CommonConst;
import com.hzb.erp.common.entity.Staff;
import com.hzb.erp.common.entity.User;
import com.hzb.erp.common.entity.WxAccess;
import com.hzb.erp.common.service.StaffService;
import com.hzb.erp.common.service.UserService;
import com.hzb.erp.security.Util.JwtUserDetails;
import com.hzb.erp.security.Util.SecurityUtils;
import com.hzb.erp.security.provider.staff.StaffUserDetailsService;
import com.hzb.erp.security.provider.user.UserUserDetailsService;
import com.hzb.erp.wechat.service.WechatService;
import com.hzb.erp.wechat.service.WxAccessService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;


@AllArgsConstructor
@Controller
@RequestMapping("/wx/login/{confName}")
@Slf4j
@EnableConfigurationProperties(value = {WxMpProperties.class, SystemConfig.class})
public class WxLoginController {

    @Autowired
    private WxAccessService wxAccessService;

    @Autowired
    private UserService userService;

    @Autowired
    private StaffService staffService;

    @Resource
    private UserUserDetailsService userUserDetailsService;
    @Resource
    private StaffUserDetailsService staffUserDetailsService;

    private final WxMpService wxService;

    private final WxMpProperties wxMpProperties;

    @GetMapping
    public ModelAndView wxLogin(HttpServletRequest request,
                                HttpServletResponse response,
                                @PathVariable String confName,
                                @RequestParam String code,
                                @RequestParam String state,
                                ModelAndView view) {

        String appid = WechatService.getAppIdByConfName(confName);

        try {
            if (!this.wxService.switchover(appid)) {
                throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
            }

            WxOAuth2AccessToken accessToken = wxService.getOAuth2Service().getAccessToken(code);
            WxOAuth2UserInfo user = wxService.getOAuth2Service().getUserInfo(accessToken, null);

            log.info("获取到微信用户信息：" + user.toString());

            WxAccess wxAccess = wxAccessService.getOrSaveRecord(user);
            log.info("获取到微信登录记录：" + wxAccess.toString());

            URL requestUrl = new URL(request.getRequestURL().toString());
            String host = requestUrl.getHost();

            Cookie cookie = new Cookie(CommonConst.WX_ACCESS_ID, String.valueOf(wxAccess.getId()));
            cookie.setDomain(host);
            cookie.setPath("/");
            response.addCookie(cookie);
            log.info("requestUrl.getHost：" + host);
            JwtUserDetails userDetails = null;
            String viewPath = null;
            if ("student".equals(state)) {
                User parent = userService.getByWxAccessId(wxAccess.getId());
                log.info("获取到User对象：" + parent);

                if (parent == null) {
                    view.addObject("url", "/s/#/pages/login/index");
                    throw new RuntimeException("您的微信未绑定家长端账号，请先注册!");
                } else {
                    userDetails = userUserDetailsService.loadUserByUsername(parent.getMobile());
                    if (userDetails != null) {
                        SecurityUtils.setStudentRole(userDetails);
                        SecurityUtils.checkUserDetails(userDetails);
                    }
                    viewPath = "/s";
                }
            } else {
                Staff staff = staffService.getByWxAccessId(wxAccess.getId());
                log.info("获取到staff对象：" + staff);
                if (staff == null) {
                    view.addObject("url", "/t/#/pages/login/index");
                    throw new RuntimeException("您的微信未绑定教师端账号，请使用账号密码登录!");
                } else {
                    userDetails = staffUserDetailsService.loadUserByUsername(staff.getMobile());
                    if (userDetails != null) {
                        SecurityUtils.checkUserDetails(userDetails);
                    }
                    viewPath = "/t";
                }
            }

            Cookie cookie1 = new Cookie(CommonConst.DEFAULT_TOKEN_NAME, buildJwtToken(userDetails));
            cookie1.setDomain(host);
            cookie1.setPath("/");
            response.addCookie(cookie1);
            view.setView(new RedirectView(viewPath, false));
            return view;

        } catch (Exception e) {
            view.setViewName("errPage.html");
            view.addObject("msg", e.getMessage());
            view.addObject("code", 500);
            return view;
        }
    }

    private String buildJwtToken(JwtUserDetails userDetails) {
        return SecurityUtils.generateToken(userDetails, SystemConfig.getJwtExpiredTtlSec());
    }
}
