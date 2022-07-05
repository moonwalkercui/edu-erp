package com.hzb.erp.service;


import com.hzb.erp.exception.BizException;
import com.hzb.erp.service.dto.SmsSendDTO;

/**
 * 手机短信接口
 */
public interface SmsManager {

    /**
     * 发送手机短信验证码,有频繁检查
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
     * 验证手机验证码是否正确
     *
     * @param scene  业务场景
     * @param mobile 手机号码
     * @param code   手机验证码
     * @return 是否通过校验 true通过，false不通过
     */
    boolean valid(String scene, String mobile, String code);

    /**
     * 验证发送是否过于频繁
     *
     * @param mobile 手机号码
     */
    void limitValid(String mobile) throws BizException;

    /**
     * 生成一个验证码
     *
     * @return 验证码
     */
    String makeSmsCode();
}
