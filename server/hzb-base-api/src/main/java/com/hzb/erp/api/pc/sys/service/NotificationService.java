package com.hzb.erp.api.pc.sys.service;

import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.common.entity.Staff;
import com.hzb.erp.service.notification.bo.NoticeBO;
import com.hzb.erp.service.notification.NoticeCodeEnum;
import org.springframework.stereotype.Service;

/**
 * @author Ryan 541720500@qq.com
 * description 对外消息发送服务
 */
@Service
public interface NotificationService {
    void sendToTeacher(NoticeCodeEnum code, NoticeBO entity, Staff teacher);

    void sendToStudent(NoticeCodeEnum code, NoticeBO entity, Student student);
}
