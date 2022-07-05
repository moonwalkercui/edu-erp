package com.hzb.erp.api.pc.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.pc.sys.entity.Message;
import com.hzb.erp.api.pc.sys.mapper.MessageMapper;
import com.hzb.erp.common.enums.MessageUserTypeEnum;
import com.hzb.erp.api.pc.sys.service.MessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 消息实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public void sendToStaffBatch(Long fromId, MessageUserTypeEnum fromType, List<Long> staffIds, String title, String content) {
        List<Message> msgList = new ArrayList<>();
        for (Long toId: staffIds) {
            msgList.add(newEntity(title, content, toId, fromId, MessageUserTypeEnum.STAFF, fromType));
        }
        send(msgList);
    }

    @Override
    public void sendToStudentBatch(Long fromId, MessageUserTypeEnum fromType, List<Long> studentIds, String title, String content) {
        List<Message> msgList = new ArrayList<>();
        for (Long toId: studentIds) {
            msgList.add(newEntity(title, content, toId, fromId, MessageUserTypeEnum.STUDENT, fromType));
        }
        send(msgList);
    }

    @Override
    public void sendToAllStaff(String title, String content) {
        Message msg = newEntity(title, content, Long.valueOf(MessageUserTypeEnum.ALL.toString()), null, MessageUserTypeEnum.STAFF, MessageUserTypeEnum.SYS);
        send(msg);
    }

    @Override
    public void sendToAllStudent(String title, String content) {
        Message msg = newEntity(title, content, Long.valueOf(MessageUserTypeEnum.ALL.toString()), null, MessageUserTypeEnum.STUDENT, MessageUserTypeEnum.SYS);
        send(msg);
    }

    @Override
    public void sendToStaff(Long fromId, MessageUserTypeEnum fromType, Long toStaffId, String title, String content) {
        Message msg = newEntity(title, content, toStaffId, fromId, MessageUserTypeEnum.STAFF, fromType);
        send(msg);
    }

    @Override
    public void sendToStudent(Long fromId, MessageUserTypeEnum fromType, Long toStudentId, String title, String content) {
        Message msg = newEntity(title, content, toStudentId, fromId, MessageUserTypeEnum.STAFF, fromType);
        send(msg);
    }

    /**
     * 创建实体方法
     * @param title
     * @param content
     * @param toId
     * @param fromId
     * @param toType
     * @param fromType
     * @return
     */
    private Message newEntity(String title, String content, Long toId, Long fromId, MessageUserTypeEnum toType, MessageUserTypeEnum fromType) {
        Message msg = new Message();
        msg.setTitle(title);
        msg.setContent(content);
        msg.setToId(toId);
        msg.setFromId(fromId);
        msg.setToType(toType);
        msg.setFromType(fromType);
        msg.setSendTime(LocalDateTime.now());
        return msg;
    }

    private void send(Message msg) {
        save(msg);
    }

    private void send(List<Message> msgList) {
        saveBatch(msgList);
    }
}
