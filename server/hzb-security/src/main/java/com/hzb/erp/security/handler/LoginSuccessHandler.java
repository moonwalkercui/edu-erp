package com.hzb.erp.security.handler;

import cn.hutool.json.JSONUtil;
import com.hzb.erp.common.configuration.SystemConfig;
import com.hzb.erp.common.entity.SysLog;
import com.hzb.erp.common.service.SysLogService;
import com.hzb.erp.security.Util.JwtUserDetails;
import com.hzb.erp.security.Util.SecurityUtils;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.security.Enums.LoginUserIdentity;
import com.hzb.erp.utils.IpUtil;
import com.hzb.erp.utils.JsonResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * description: 登陆验证成功处理
 */
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private SysLogService sysLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        JwtUserDetails user = (JwtUserDetails) authentication.getPrincipal();
        String jwtToken = SecurityUtils.generateToken(user, SystemConfig.getJwtExpiredTtlSec());

        SysLog olog = new SysLog();
        // 处理家长
        if (LoginUserIdentity.USER.getCode() == user.getIdentity()) {
//            UserAuthService.updateUserLoginInfo(request, user.getId());
            olog.setType("家长端登录");
        } else {
            olog.setType("老师登录");
        }
        olog.setOperator(user.getId());
        olog.setInfo("登录成功");
        olog.setPath(request.getRequestURI());
        olog.setUrl(String.valueOf(request.getRequestURL()));
        olog.setParam(user.getName());
        olog.setMethod(request.getMethod());
        olog.setIp(IpUtil.getIpAddr(request));
        olog.setBrowserName(IpUtil.getBrowserName(request));
        olog.setBrowserVer(IpUtil.getBrowserVersion(request));
        olog.setOsName(IpUtil.getOsName(request));
        olog.setAddTime(LocalDateTime.now());
        sysLogService.addOne(olog);

        //签发token
        CommonUtil.jsonResponse(response, JSONUtil.toJsonStr(JsonResponseUtil.data(jwtToken)));
    }
}