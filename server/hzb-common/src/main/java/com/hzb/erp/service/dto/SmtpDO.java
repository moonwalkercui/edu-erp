package com.hzb.erp.service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * 邮件实体
 */
@Data
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SmtpDO implements Serializable {

    private static final long serialVersionUID = 9787156257241506L;

    /**
     * 主键ID
     */
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 主机
     */
    @ApiModelProperty(name = "host", value = "主机", required = false)
    private String host;
    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty(name = "username", value = "用户名", required = true)
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(name = "password", value = "密码", required = false)
    private String password;
    /**
     * 最后发信时间
     */
    @ApiModelProperty(name = "last_send_time", value = "最后发信时间", required = false, hidden = true)
    private Long lastSendTime;
    /**
     * 已发数
     */
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "send_count", value = "已发数", required = false, hidden = true)
    private Integer sendCount;
    /**
     * 最大发信数
     */
    @ApiModelProperty(name = "max_count", value = "最大发信数", required = false)
    private Integer maxCount;
    /**
     * 发信邮箱
     */
    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(name = "mail_from", value = "发信邮箱", required = false)
    private String mailFrom;
    /**
     * 端口
     */
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "port", value = "端口", required = false)
    private Integer port;
    /**
     * ssl是否开启
     */
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "open_ssl", value = "ssl是否开启", required = false)
    private Integer openSsl;

}