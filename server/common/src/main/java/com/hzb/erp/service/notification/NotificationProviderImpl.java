package com.hzb.erp.service.notification;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzb.erp.common.entity.SettingNotice;
import com.hzb.erp.common.service.SettingNoticeService;
import com.hzb.erp.service.notification.bo.ContactTypeBO;
import com.hzb.erp.service.notification.bo.NoticeBO;
import com.hzb.erp.service.notification.bo.ToEmailBO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ryan 541720500@qq.com
 * description 实现类
 */
@Service
public class NotificationProviderImpl implements NotificationProvider {

    @Autowired
    private WxNoticeSender wxNoticeSender;
    @Autowired
    private SmsNoticeSender smsNoticeSender;
    @Autowired
    private EmailNoticeSender emailNoticeSender;
    @Autowired
    private SettingNoticeService settingNoticeService;

    @Override
    public void sendNotice(NoticeCodeEnum code, NoticeBO param, ContactTypeBO contactType) {

        QueryWrapper<SettingNotice> qw = new QueryWrapper<>();
        qw.eq("code", code.getCode());
        SettingNotice one = settingNoticeService.getOne(qw);

        doSend(one, param, contactType, code);

    }


    private void doSend(SettingNotice one, NoticeBO param, ContactTypeBO contactType, NoticeCodeEnum code) {

        System.out.println("======消息设置=======");
        System.out.println(one.toString());

        // 可以发送微信
        if (one.getWxOn() != null && one.getWxOn() && StringUtils.isNotBlank(one.getWxCode()) && StringUtils.isNotBlank(contactType.getWxOpenId())) {
            wxNoticeSender.send(contactType.getWxOpenId(), one.getWxCode(), param, code);
        }

        // 可以发送短信
        if (one.getSmsOn() != null && one.getSmsOn() && StringUtils.isNotBlank(one.getSmsCode()) && StringUtils.isNotBlank(contactType.getMobile())) {
            smsNoticeSender.send(contactType.getMobile(), one.getSmsCode(), param, code);
        }

        // 可以发送邮件
        if (one.getEmailOn() != null && one.getEmailOn() && StringUtils.isNotBlank(contactType.getEmail()) && param.getContent() != null) {
            // 可以发送email
            ToEmailBO bo = new ToEmailBO();
            bo.setTos(new String[]{contactType.getEmail()});
            bo.setSubject(param.getSubject());
            bo.setContent(param.getContent());
            emailNoticeSender.send(bo);
        }
    }


}
