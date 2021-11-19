package com.hzb.erp.service.notification.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ryan 541720500@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToEmailBO {
    /**
     * 邮件接收方，可多人
     */
    private String[] tos;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;

}
