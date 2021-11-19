package com.hzb.erp.service.notification;

import com.hzb.erp.service.notification.bo.NoticeBO;

/**
 * <p> </p>
 *
 * @author Ryan 541720500@qq.com
 */
public interface WxNoticeSender {
    Boolean send( String openId, String templateId, NoticeBO param, NoticeCodeEnum code);
}
