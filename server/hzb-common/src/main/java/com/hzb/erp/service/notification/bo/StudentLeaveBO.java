package com.hzb.erp.service.notification.bo;

import lombok.Data;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class StudentLeaveBO extends NoticeBO {
    String lessonTitle;
    String date;
    String startTime;
}
