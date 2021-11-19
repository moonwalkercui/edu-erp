package com.hzb.erp.service.notification;

import com.hzb.erp.service.notification.bo.NoticeBO;

/**
 * <p> </p>
 *
 * @author Ryan 541720500@qq.com
 */
public interface SmsNoticeSender {
    Boolean send( String mobile, String smsId, NoticeBO param, NoticeCodeEnum code);
}
