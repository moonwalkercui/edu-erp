package com.hzb.erp.service;

import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.service.cache.SmsCodeCache;
import com.hzb.erp.service.cache.SmsSendLimitCache;
import com.hzb.erp.service.dto.SmsSendDTO;
import com.hzb.erp.service.enums.SmsSceneType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 手机短信实现
 */
@Service
@Slf4j
public class SmsManagerImpl implements SmsManager {

//    @Autowired
//    private SmsPlatformManager smsPlatformManager;
//
//    @Autowired
//    private SettingManager settingManager;
//
//    @Autowired
//    private MessageTemplateClient messageTemplateClient;
//    @Autowired
//    private Cache cache;

    private static final String ALI_ACCESS_KEY = "LTAI5tLMp6QxQsNjMJHUq2T9";
    private static final String ALI_ACCESS_SECRET = "Hi3FtFZ32s0IBbi6KicKeeGuZlwu9t";
    private static final String ALI_SIGN_NAME = "双惠德";
    private static final String ALI_REGION_ID = "cn-hangzhou";

    //    @Autowired
//    private SmsAliYunPlugin smsAliYunPlugin;

    @Override
    public boolean sendCode(SmsSendDTO dto) {

        long remainingSec = SmsSendLimitCache.remainingSec(dto.getMobile());
        if (remainingSec > 0) {
            throw new BizException("短信发送过于频繁，请" + remainingSec + "秒后重发");
        }

        return send(dto);
    }

    @Override
    public boolean send(SmsSendDTO dto) {

//        SmsSceneType scene = getSceneEnum(dto.getScene());
//        SmsCodeCache.put(dto.getContent(), dto.getMobile(), scene);

        // 发送短信实现

        log.info("发送短信");
        log.info(dto.toString());

        // 在调查清楚为什么正式环境发不了短信前
        // 使用传统方法发送
//        String tempCode;
//        switch (scene) {
//            case STUDENT_FORGETPW:
//                tempCode = "SMS_223835183";
//                break;
//            default:
//                tempCode = "SMS_223835183";
//                break;
//        }
        return true;

//        // 随机生成的动态码
//        String dynamicCode = "";
//
//        MessageTemplateDO template = messageTemplateClient.getModel(MessageCodeEnum.MOBILECODESEND);
//        String siteSettingJson = settingManager.get(SettingGroup.SITE);
//        SiteSetting siteSetting = JsonUtil.jsonToObject(siteSettingJson, SiteSetting.class);
//        if (siteSetting.getTestMode().equals(1)) {
//            dynamicCode = "111111";
//        } else {
//            dynamicCode = RandomCreate.getRandomCode();
//        }
//            DefaultProfile profile = DefaultProfile.getProfile(ALI_REGION_ID, ALI_ACCESS_KEY, ALI_ACCESS_SECRET);
//            IAcsClient client = new DefaultAcsClient(profile);
//            CommonRequest request = new CommonRequest();
//            request.setSysMethod(MethodType.POST);
//            request.setSysDomain("dysmsapi.aliyuncs.com");
//            request.setSysVersion("2017-05-25");
//            request.setSysAction("SendSms");
//            request.putQueryParameter("RegionId", "cn-hangzhou");
//            request.putQueryParameter("PhoneNumbers", mobile);
//            request.putQueryParameter("SignName", ALI_SIGN_NAME);//
//            request.putQueryParameter("TemplateCode", ALI_SMS_TEMP_CODE);
//            request.putQueryParameter("TemplateParam", JSONObject.toJSONString(dto.getDataMap));
//
//            try {
//                CommonResponse response = client.getCommonResponse(request);
//                JSONObject object = JSONObject.parseObject(response.getData());
//                if (!(object.get("Message").equals("OK") && object.get("Code").equals("OK"))) {
//                    System.out.println("ERROR: 发送短信失败!");
//                }
//            } catch (ServerException e) {
//                e.printStackTrace();
//            } catch (ClientException e) {
//                e.printStackTrace();
//            }
//        //缓存中记录验证码
//        this.record(sceneType.name(), mobile, dynamicCode);
    }

    @Override
    public boolean valid(String scene, String mobile, String code) {
        SmsSceneType sceneEnum = getSceneEnum(scene);
        return SmsCodeCache.valid(mobile, code, sceneEnum);
    }


    @Override
    public void record(String scene, String mobile, String code) {
//        System.out.println(CachePrefix.SMS_CODE.getPrefix() + scene + "_" + mobile);
//        cache.put(CachePrefix.SMS_CODE.getPrefix() + scene + "_" + mobile, code, javashopConfig.getSmscodeTimout());
    }

    @Override
    public String makeSmsCode() {
        return "" + (int) ((Math.random() * 9 + 1) * 100000);
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
