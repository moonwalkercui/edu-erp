package com.hzb.erp.wechat.controller;

import com.hzb.erp.common.configuration.WxMpProperties;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.wechat.service.WechatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/wx/jsapi/{confName}")
@EnableConfigurationProperties(WxMpProperties.class)
@Slf4j
public class WxJsapiController {
    private final WxMpService wxService;
    private final WxMpProperties wxMpProperties;

    @GetMapping("/getJsapiTicket")
    public WxJsapiSignature getJsapiTicket(@PathVariable String confName, @Param(value = "url") String url) throws WxErrorException {
        String appid = WechatService.getAppIdByConfName(wxMpProperties, confName);
        final WxJsapiSignature jsapiSignature;
        try {
            jsapiSignature = this.wxService.switchoverTo(appid).createJsapiSignature(url);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BizException(e.getMessage());
        }
        return jsapiSignature;
    }

}
