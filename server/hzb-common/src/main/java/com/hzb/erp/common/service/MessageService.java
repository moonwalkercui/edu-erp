package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.LessonTeacher;
import com.hzb.erp.common.entity.Message;
import com.hzb.erp.common.entity.Staff;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.enums.MessageUserTypeEnum;
import com.hzb.erp.service.notification.NoticeCodeEnum;
import com.hzb.erp.service.notification.bo.NoticeBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ryan 541720500@qq.com
 * description 站内信发送服务
 */
@Service
public interface MessageService extends IService<Message> {


    /**
     * 批量发送给老师
     */
    void sendToStaffBatch(Long fromId, MessageUserTypeEnum fromType, List<Long> staffIds, String title, String content);

    /**
     * 批量发送给学生
     */
    void sendToStudentBatch(Long fromId, MessageUserTypeEnum fromType, List<Long> studentIds, String title, String content);

    /**
     * 发送给所有老师
     * 发送者只能是系统
     */
    void sendToAllStaff(String title, String content);

    /**
     * 发送给所有学生
     * 发送者只能是系统
     */
    void sendToAllStudent(String title, String content);

    /**
     * 发送给老师
     */
    void sendToStaff(Long fromId, MessageUserTypeEnum fromType, Long toStaffId, String title, String content);

    /**
     * 发送给学生
     */
    void sendToStudent(Long fromId, MessageUserTypeEnum fromType, Long toStudentId, String title, String content);
}
