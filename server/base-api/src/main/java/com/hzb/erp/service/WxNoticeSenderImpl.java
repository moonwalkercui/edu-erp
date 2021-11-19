package com.hzb.erp.service;

import com.hzb.erp.service.notification.NoticeCodeEnum;
import com.hzb.erp.service.notification.WxNoticeSender;
import com.hzb.erp.service.notification.bo.*;
import com.hzb.erp.wechat.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan 541720500@qq.com
 * description 微信发送器
 */
@Service
@Slf4j
public class WxNoticeSenderImpl implements WxNoticeSender {

    @Autowired
    private WechatService wechatService;

    /**
     * 发送消息
     */
    @Override
    public Boolean send(String openId, String templateId, NoticeBO param, NoticeCodeEnum code) {

        if (StringUtils.isBlank(openId)) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String nowTime = dateFormat.format(new Date());

        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)
                .templateId(templateId)
                .url("")
                .build();


        Map<String, Object> dataMap = new HashMap<>();
        String content;
        switch (code) {
            case STUDENT_LESSON_START:
            case TEACHER_LESSON_START:

//                详细内容
//            {{first.DATA}}
//            课程名称：{{keyword1.DATA}}
//            上课时间：{{keyword2.DATA}}
//            上课地点：{{keyword3.DATA}}
//            {{remark.DATA}}

                LessonStartBO bo1 = (LessonStartBO) param;
                templateMessage
                        .addData(new WxMpTemplateData("first", bo1.getContent()))
                        .addData(new WxMpTemplateData("keyword1", bo1.getCourseName()))
                        .addData(new WxMpTemplateData("keyword2", bo1.getDate() + " " + bo1.getStartTime()))
                        .addData(new WxMpTemplateData("keyword3", bo1.getClassroom()))
                        .addData(new WxMpTemplateData("remark", "请提前15分钟到课."));
                System.out.println("=====bo1");
                System.out.println(bo1);
                content = bo1.getContent();
                break;

            case STUDENT_SIGN:

//            {{first.DATA}}
//            课程名称：{{keyword1.DATA}}
//            剩余课时：{{keyword2.DATA}}
//            上课时间：{{keyword3.DATA}}
//            消课情况：{{keyword4.DATA}}
//            {{remark.DATA}}

                StudentSignBO bo2 = (StudentSignBO) param;
                templateMessage
                        .addData(new WxMpTemplateData("first", bo2.getContent()))
                        .addData(new WxMpTemplateData("keyword1", bo2.getLessonTitle()))
                        .addData(new WxMpTemplateData("keyword2", "请登录学生端查看"))
                        .addData(new WxMpTemplateData("keyword3", bo2.getDate() + " " + bo2.getTime()))
                        .addData(new WxMpTemplateData("keyword4", bo2.getState()))
                        .addData(new WxMpTemplateData("remark", ""));
                System.out.println("=====bo2");
                System.out.println(bo2);
                content = bo2.getContent();
                break;

            case STUDENT_LESSON_ONCHANGE:
            case TEACHER_LESSON_ONCHANGE:

//            {{first.DATA}}
//            课程名称：{{keyword1.DATA}}
//            课程说明：{{keyword2.DATA}}
//            {{remark.DATA}}

                LessonChangeBO bo3 = (LessonChangeBO) param;
                templateMessage
                        .addData(new WxMpTemplateData("first", bo3.getContent()))
                        .addData(new WxMpTemplateData("keyword1", bo3.getLessonTitle()))
                        .addData(new WxMpTemplateData("keyword2", "课程状态变为" + bo3.getNewState()))
                        .addData(new WxMpTemplateData("remark", "原上课时间" + bo3.getDate() + " " + bo3.getStartTime()));
                System.out.println("=====bo3");
                System.out.println(bo3);
                content = bo3.getContent();
                break;

            case STUDENT_NEW_CONTRACT:

//            {{first.DATA}}
//            报名内容：{{keyword1.DATA}}
//            报名结果：{{keyword2.DATA}}
//            {{remark.DATA}}

                NewContractBO bo4 = (NewContractBO) param;
                templateMessage
                        .addData(new WxMpTemplateData("first", bo4.getContent()))
                        .addData(new WxMpTemplateData("keyword1", bo4.getCourseName()))
                        .addData(new WxMpTemplateData("keyword2", "已报名成功"))
                        .addData(new WxMpTemplateData("remark", "课时数：" + bo4.getLessonCount() + ", 金额：" + bo4.getAmount()));

                System.out.println("=====bo4");
                System.out.println(bo4);
                content = bo4.getContent();
                break;

            case STUDENT_LESSON_COUNT_LESS:
            case TEACHER_STUDENT_LESSONLESS:

//            {{first.DATA}}
//            学员名称：{{keyword1.DATA}}
//            剩余课时：{{keyword2.DATA}}
//            {{remark.DATA}}

                LessonNotEnoughBO bo5 = (LessonNotEnoughBO) param;
                templateMessage
                        .addData(new WxMpTemplateData("first", bo5.getContent()))
                        .addData(new WxMpTemplateData("keyword1", bo5.getStudentName()))
                        .addData(new WxMpTemplateData("keyword2", bo5.getLessonCount()))
                        .addData(new WxMpTemplateData("remark", "该课程过期时间" + bo5.getExpireDate()));
                System.out.println("=====bo5");
                System.out.println(bo5);
                content = bo5.getContent();
                break;

            case TEACHER_STUDENT_LEAVE:

//            {{first.DATA}}
//            学生姓名：{{keyword1.DATA}}
//            课程名称：{{keyword2.DATA}}
//            原上课时间：{{keyword3.DATA}}
//            {{remark.DATA}}

                StudentLeaveBO bo6 = (StudentLeaveBO) param;
                templateMessage
                        .addData(new WxMpTemplateData("first", bo6.getContent()))
                        .addData(new WxMpTemplateData("keyword1", bo6.getStudentName()))
                        .addData(new WxMpTemplateData("keyword2", bo6.getLessonTitle()))
                        .addData(new WxMpTemplateData("keyword3", bo6.getDate() + " " + bo6.getStartTime()))
                        .addData(new WxMpTemplateData("remark", " "));
                System.out.println("=====bo6");
                System.out.println(bo6);
                content = bo6.getContent();
                break;

            default:
                throw new RuntimeException("未知NoticeBO类型");
        }

        log.info("=======微信 发送消息===========");
        log.info("templateId：" + templateId);
        log.info("参数：" + param.toString());
        log.info("openId：" + openId);

        try {
            wechatService.sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("微信模板消息发送异常：" + e.getMessage());
            return false;
        }
        return true;
    }

}
