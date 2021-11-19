package com.hzb.erp.service.notification;

import com.hzb.erp.service.notification.bo.ToEmailBO;

/**
 * <p> </p>
 *
 * @author Ryan 541720500@qq.com
 */
public interface EmailNoticeSender {
    void send(ToEmailBO emailBO);
}
