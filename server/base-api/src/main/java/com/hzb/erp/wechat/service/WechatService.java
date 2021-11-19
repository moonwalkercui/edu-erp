package com.hzb.erp.wechat.service;

import com.hzb.erp.common.configuration.WxMpProperties;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Service
@AllArgsConstructor
@EnableConfigurationProperties(WxMpProperties.class)
public class WechatService {

    @Autowired
    protected WxMpService wxService;

    private final WxMpProperties wxMpProperties;

//    private final WxMpProperties wxMpProperties;

//    public String redirectUrl(HttpServletRequest request, String confName) throws MalformedURLException {
//        String appid = WechatService.getAppIdByConfName(wxMpProperties, confName);
//
//        URL requestUrl = new URL(request.getRequestURL().toString());
//        String url = this.wxService.switchoverTo(appid).getOAuth2Service().buildAuthorizationUrl(
//                String.format("%s://%s/wx/redirect/%s/login", requestUrl.getProtocol(), requestUrl.getHost(), appid),
//                WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
//        return url;
//    }

    /**
     * 通过配置名称获取配置
     * @param wxMpProperties WxMpProperties
     * @param confName 参数名 null表示取第一个公众号配置
     */
    public static WxMpProperties.MpConfig getConfigByName(WxMpProperties wxMpProperties, String confName) {
        List<WxMpProperties.MpConfig> configs = wxMpProperties.getConfigs();
        if(StringUtils.isBlank(confName)) {
            return configs.get(0);
        }
        for (WxMpProperties.MpConfig conf : configs) {
            System.out.println(conf.getName().equals(confName));
            if (conf.getName().equals(confName)) {
                return conf;
            }
        }
        return null;
    }

    /**
     * 通过配置名称获取confName
     */
    public static String getAppIdByConfName(WxMpProperties wxMpProperties, String confName) {
        WxMpProperties.MpConfig mpConfig = getConfigByName(wxMpProperties, confName);
        if (mpConfig == null) {
            throw new RuntimeException("配置参数APPID设置错误");
        }
        return mpConfig.getAppId();
    }

    /**
     * 使用自定义公众号配置发送模板消息
     * */
    public void sendTemplateMsg(WxMpTemplateMessage templateMessage, String configName) throws WxErrorException {
        String appid = WechatService.getAppIdByConfName(wxMpProperties, configName);
        String msgId = this.wxService.switchoverTo(appid).getTemplateMsgService().sendTemplateMsg(templateMessage);
        System.out.println("========模板消息发送结果msgId：" + msgId);
    }

    /**
    * 使用默认公众号配置发送模板消息
    * */
    public void sendTemplateMsg(WxMpTemplateMessage templateMessage) throws WxErrorException {
        String appid = WechatService.getAppIdByConfName(wxMpProperties, null);
        String msgId = this.wxService.switchoverTo(appid).getTemplateMsgService().sendTemplateMsg(templateMessage);
        System.out.println("========模板消息发送结果msgId：" + msgId);
    }

}
