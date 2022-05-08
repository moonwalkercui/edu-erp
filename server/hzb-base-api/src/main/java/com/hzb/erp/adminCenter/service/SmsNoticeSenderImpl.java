package com.hzb.erp.adminCenter.service;

import com.hzb.erp.service.SmsManager;
import com.hzb.erp.service.dto.SmsSendDTO;
import com.hzb.erp.service.notification.NoticeCodeEnum;
import com.hzb.erp.service.notification.SmsNoticeSender;
import com.hzb.erp.service.notification.bo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan 541720500@qq.com
 * description 短信发送器
 */
@Service
@Slf4j
public class SmsNoticeSenderImpl implements SmsNoticeSender {

    @Autowired
    private SmsManager smsManager;

    /**
     * 发送消息
     */
    @Override
    public Boolean send(String mobile, String smsId, NoticeBO param, NoticeCodeEnum code) {

        Map<String, Object> dataMap = new HashMap<>();
        String content;
        switch (code) {
            case STUDENT_LESSON_START:
            case TEACHER_LESSON_START:
                LessonStartBO bo1 = (LessonStartBO) param;
                System.out.println("=====bo1");
                System.out.println(bo1);
                content = bo1.getContent();
                break;
            case STUDENT_SIGN:
                StudentSignBO bo2 = (StudentSignBO) param;
                System.out.println("=====bo2");
                System.out.println(bo2);
                content = bo2.getContent();
                break;
            case STUDENT_LESSON_ONCHANGE:
                LessonChangeBO bo3 = (LessonChangeBO) param;
                System.out.println("=====bo3");
                System.out.println(bo3);
                content = bo3.getContent();
                break;
            case STUDENT_NEW_CONTRACT:
                NewContractBO bo4 = (NewContractBO) param;
                System.out.println("=====bo4");
                System.out.println(bo4);
                content = bo4.getContent();
                break;
            case STUDENT_LESSON_COUNT_LESS:
            case TEACHER_STUDENT_LESSONLESS:
                LessonNotEnoughBO bo5 = (LessonNotEnoughBO) param;
                System.out.println("=====bo5");
                System.out.println(bo5);
                content = bo5.getContent();
                break;

            case TEACHER_STUDENT_LEAVE:
                StudentLeaveBO bo6 = (StudentLeaveBO) param;
                System.out.println("=====bo6");
                System.out.println(bo6);
                content = bo6.getContent();
                break;

            case TEACHER_LESSON_ONCHANGE:
                LessonChangeBO bo7 = (LessonChangeBO) param;
                System.out.println("=====bo7");
                System.out.println(bo7);
                content = bo7.getContent();
                break;
            default:
                throw new RuntimeException("未知NoticeBO类型");
        }

        log.info("=======短信发送消息===========");

        if (StringUtils.isBlank(mobile)) {
            return null;
        }
        SmsSendDTO dto = new SmsSendDTO();
        dto.setMobile(mobile);
        dto.setScene(code.getCode());
        dto.setContent(content);
        dto.setDataMap(dataMap);
        dto.setTempId(smsId);

        log.info(dto.toString());
        smsManager.send(dto);

        return null;
    }

}
