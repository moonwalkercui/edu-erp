package com.hzb.erp.service.notification.bo;

import lombok.Data;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class StudentSignBO extends NoticeBO {
    // 课次标题
    String lessonTitle;
    // 签到时间
    String date;
    String time;
    // 签到状态
    String state;
}
