package com.hzb.erp.service.notification.bo;

import lombok.Data;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class LessonStartBO extends NoticeBO {
    String date;
    String courseName;
    String className;
    String classroom;
    String startTime;
    String endTime;
}
