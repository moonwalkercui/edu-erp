package com.hzb.erp.service;


import com.hzb.erp.service.dto.SmsSendDTO;

/**
 * 手机短信接口
 */
public interface SmsManager {

    /**
     * 发送手机短信验证码
     *
     * @param smsSendDTO
     */
    boolean sendCode(SmsSendDTO smsSendDTO);

    /**
     * 发送手机短信
     *
     * @param smsSendDTO
     */
    boolean send(SmsSendDTO smsSendDTO);

    /**
     * 验证手机验证码
     *
     * @param scene  业务场景
     * @param mobile 手机号码
     * @param code   手机验证码
     * @return 是否通过校验 true通过，false不通过
     */
    boolean valid(String scene, String mobile, String code);

    /**
     * 在缓存中记录验证码
     *
     * @param scene  业务场景
     * @param mobile 手机号码
     * @param code   手机验证码
     */
    void record(String scene, String mobile, String code);

    String makeSmsCode();
}
