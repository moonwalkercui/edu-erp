package com.hzb.erp.wechat.handler;

import com.hzb.erp.wechat.service.WechatService;
import com.hzb.erp.wechat.service.WxAccessService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UnsubscribeHandler extends AbstractHandler {
    @Autowired
    private WxAccessService wxAccessService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        WechatService.setConfig(wxMpService);
        String openId = wxMessage.getFromUser();
        this.logger.info("取消关注用户 OPENID: " + openId);
        // 更新本地数据库为取消关注状态
        wxAccessService.unsubscribe(openId);
        return null;
    }

}
