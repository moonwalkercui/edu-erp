package com.hzb.erp.service.notification;

import com.hzb.erp.service.notification.bo.ContactTypeBO;
import com.hzb.erp.service.notification.bo.NoticeBO;

/**
 * @author Ryan 541720500@qq.com
 * description 通知发送器
 */
public interface NotificationProvider {

    /**
     * 发送消息
     *
     * @param code  发送编码 通过该码可以找到发送方式 见setting_notice表
     * @param param 信息参数
     * @return 是否发送成功 null为未发送
     */
    void sendNotice(NoticeCodeEnum code, NoticeBO param, ContactTypeBO contactType);
}
