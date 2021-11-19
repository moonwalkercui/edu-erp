package com.hzb.erp.service.notification.bo;

import lombok.Data;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class NewContractBO extends NoticeBO {
    String courseName;
    // 金额
    String amount;
    // 课次数
    String lessonCount;
    String expireDate;
    String startDate;
}
