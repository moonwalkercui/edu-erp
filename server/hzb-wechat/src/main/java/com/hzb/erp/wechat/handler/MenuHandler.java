package com.hzb.erp.wechat.handler;

import com.hzb.erp.common.configuration.SystemConfig;
import com.hzb.erp.wechat.service.WechatService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.EventType;

@AllArgsConstructor
@Component
@EnableConfigurationProperties(SystemConfig.class)
public class MenuHandler extends AbstractHandler {

    private final SystemConfig systemConfig;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        WechatService.setConfig(weixinService);
        String msg = String.format("type:%s, event:%s, key:%s",
                wxMessage.getMsgType(), wxMessage.getEvent(),
                wxMessage.getEventKey());
        if (EventType.VIEW.equals(wxMessage.getEvent())) {
            return null;
        }
        if (EventType.CLICK.equals(wxMessage.getEvent()) && "GET_ADMIN_URL".equals(wxMessage.getEventKey())) {
            msg = "教务系统管理端演示地址: " + systemConfig.getDomain() + "/";
        }
        return WxMpXmlOutMessage.TEXT().content(msg)
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
    }

}
