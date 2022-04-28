package com.hzb.erp.wechat.config;

import lombok.Data;

/**
 * 微信公众号配置
 * 20220313 Ryan 取消从配置文件读取的方式，改为数据库读取
 */
@Data
public class WxMpConfig {
    /**
     * 微信公众号配置名称
     */
    private String name;
    /**
     * 设置微信公众号的appid
     */
    private String appId;

    /**
     * 设置微信公众号的app secret
     */
    private String secret;

    /**
     * 设置微信公众号的token
     */
    private String token;

    /**
     * 设置微信公众号的EncodingAESKey
     */
    private String aesKey;

}
