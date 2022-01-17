package com.hzb.erp.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 短信业务传递参数使用vo
 */
@Data
public class SmsSendDTO {
    /**
     * 手机号码
     */
    @NotBlank(message = "缺少手机号")
    private String mobile;

    /**
     * 发送类型 这个类型是前端传过来的，与SmsSceneType的code对应
     */
    @NotBlank(message = "缺少类型")
    private String scene;

    /**
     * 手机短信内容 必要情况用
     */
    private String content;

    /**
     * 模板id
     */
    private String tempId;

    private Map<String, Object> dataMap;

}
