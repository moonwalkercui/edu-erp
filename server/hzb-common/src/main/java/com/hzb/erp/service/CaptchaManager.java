package com.hzb.erp.service;

/**
 * 图片验证码业务层
 */
public interface CaptchaManager {
    /**
     * 图片验证
     *
     * @param code 验证码
     * @param uuid 唯一码
     * @return
     */
    boolean valid(String code, String uuid);

    /**
     * 图片生成
     *
     * @param uuid 唯一码
     */
    void writeCode(String uuid);

}
