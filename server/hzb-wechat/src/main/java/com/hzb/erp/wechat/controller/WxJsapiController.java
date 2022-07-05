package com.hzb.erp.wechat.controller;

import com.hzb.erp.exception.BizException;
import com.hzb.erp.wechat.service.WechatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wx/jsapi/{confName}")
@Slf4j
public class WxJsapiController{

    private final WxMpService wxService;

    @GetMapping("/getJsapiTicket")
    public WxJsapiSignature getJsapiTicket(@PathVariable String confName, @Param(value = "url") String url) {
        WechatService.setConfig(wxService);
        String appid = WechatService.getAppIdByConfName(confName);
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
