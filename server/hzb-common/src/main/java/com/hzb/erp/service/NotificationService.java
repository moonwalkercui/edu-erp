package com.hzb.erp.service;

import com.hzb.erp.common.entity.Staff;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.entity.User;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.service.StaffService;
import com.hzb.erp.common.service.StudentService;
import com.hzb.erp.common.service.UserService;
import com.hzb.erp.service.notification.bo.ContactTypeBO;
import com.hzb.erp.service.notification.bo.NoticeBO;
import com.hzb.erp.service.notification.NoticeCodeEnum;
import com.hzb.erp.service.notification.NotificationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Ryan 541720500@qq.com
 * description 消息发送服务
 */
@Service
@Slf4j
public class NotificationService {

    @Autowired
    private NotificationProvider notificationProvider;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private UserService userService;

    @Async
    public void sendToTeacher(NoticeCodeEnum code, NoticeBO entity, Staff teacher) {
        log.info("====消息发送给老师===>{}", teacher.getName());
        validBo(code, entity);

        ContactTypeBO bo = new ContactTypeBO();
        bo.setMobile(teacher.getMobile());
        bo.setEmail(teacher.getEmail());
        bo.setWxOpenId(staffService.getWxOpenid(teacher.getId()));

        notificationProvider.sendNotice(code, entity, bo);
    }

    @Async
    public void sendToStudent(NoticeCodeEnum code, NoticeBO entity, Student student) {
        log.info("====消息发送给学生===>{}", student.getName());
        validBo(code, entity);

        ContactTypeBO bo = new ContactTypeBO();
        User user = studentService.getUser(student);
        bo.setMobile(user.getMobile());
        bo.setWxOpenId(userService.getWxOpenid(user.getId()));
        bo.setEmail(user.getEmail());

        notificationProvider.sendNotice(code, entity, bo);
    }

    /**
     * 检查传过来的bo是否是规定的类型
     */
    private void validBo(NoticeCodeEnum code, Object entity) {
        if (entity == null) {
            throw new BizException("信息实体信息为空");
        }
//        Class<? extends NoticeBO> bo = code.getBo();
//        if (bo != entity.getClass()) {
//            throw new BizException("信息实体类型有误");
//        }
    }

}
