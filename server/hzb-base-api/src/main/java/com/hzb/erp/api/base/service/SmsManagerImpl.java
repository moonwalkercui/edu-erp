package com.hzb.erp.api.base.service;

import cn.hutool.json.JSONUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.hzb.erp.exception.BizException;
import com.hzb.erp.common.service.SettingService;
import com.hzb.erp.service.SmsManager;
import com.hzb.erp.service.cache.SmsCodeCache;
import com.hzb.erp.service.cache.SmsSendLimitCache;
import com.hzb.erp.service.dto.SmsSendDTO;
import com.hzb.erp.service.enums.SettingNameEnum;
import com.hzb.erp.service.enums.SmsSceneType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 手机短信实现
 * todo 请根据自己的短信服务商自行实现 send 方法
 */
@Service
@Slf4j
public class SmsManagerImpl implements SmsManager {

    @Autowired
    private SettingService settingService;

    @Override
    public boolean sendCode(SmsSendDTO dto) {
        String codeTemplateId = settingService.strValue(SettingNameEnum.SMS_CODE_TEMPLATE.getCode());
        dto.setTempId(codeTemplateId);
        limitValid(dto.getMobile());
        return send(dto);
    }

    // 阿里云发送短信实现，其他短信自行修改该方法即可
    @Override
    public boolean send(SmsSendDTO dto) {

        String aliAccessKey = settingService.strValue(SettingNameEnum.SMS_ACCESS_KEY.getCode());
        String aliAccessSecret = settingService.strValue(SettingNameEnum.SMS_ACCESS_SECRET.getCode());
        String aliSignName = settingService.strValue(SettingNameEnum.SMS_SIGN_NAME.getCode());

        if (StringUtils.isBlank(aliAccessKey) || StringUtils.isBlank(aliAccessSecret) || StringUtils.isBlank(aliSignName)) {
            throw new BizException("短信系统配置出错");
        }

        if (StringUtils.isBlank(dto.getMobile())) {
            log.error("缺少手机号");
            return false;
        }

        if (StringUtils.isBlank(dto.getTempId()) && StringUtils.isBlank(dto.getContent())) {
            log.error("短信模板或内容未设置");
            return false;
        }

        String regionId = "cn-hangzhou";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, aliAccessKey, aliAccessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setSysRegionId(regionId);
        request.setPhoneNumbers(dto.getMobile());
        request.setSignName(aliSignName);
        request.setTemplateCode(dto.getTempId());
        request.setTemplateParam(JSONUtil.toJsonStr(dto.getDataMap()));
        try {
            SendSmsResponse response = client.getAcsResponse(request);
            if ("OK".equals(response.getCode()) && "OK".equals(response.getMessage())) {
                return true;
            } else {
                log.error("短信发送失败!");
                return false;
            }
        } catch (ClientException e) {
            log.error("短信发送异常:" + e.toString());
            throw new BizException("短信发送异常");
        }
    }

    @Override
    public boolean valid(String scene, String mobile, String code) {
        SmsSceneType sceneEnum = getSceneEnum(scene);
        return SmsCodeCache.valid(mobile, code, sceneEnum);
    }

    /**
    * 验证短信发送频率，并缓存验证码
    * */
    @Override
    public void limitValid(String mobile) {
        long remainingSec = SmsSendLimitCache.remainingSec(mobile);
        if (remainingSec > 0) {
            throw new BizException("短信发送过于频繁，请" + remainingSec + "秒后重发");
        }
    }

    /**
     * 生成5位验证码
     */
    @Override
    public String makeSmsCode() {
        return "" + (int) ((Math.random() * 9 + 1) * 1000);
    }

    private SmsSceneType getSceneEnum(String scene) {
        for (SmsSceneType each : SmsSceneType.class.getEnumConstants()) {
            if (each.getCode().equals(scene)) {
                return each;
            }
        }
        throw new BizException("短信场景参数有误");
    }
}
