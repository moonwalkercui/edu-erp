package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hzb.erp.common.enums.GenderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 微信登录记录表
 * </p>
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "WxAccess对象", description = "微信登录记录表")
public class WxAccess implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "微信openid")
    private String openid;

    @ApiModelProperty(value = "微信unionid")
    private String unionid;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "添加时间")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "添加时间")
    private LocalDateTime latestLogin;

    @ApiModelProperty(value = "是否取消关注")
    private Boolean unsubscribe;

    @ApiModelProperty(value = "头像")
    private String headImg;

    @ApiModelProperty(value = "取消关注事件")
    private LocalDateTime unsubTime;

    @ApiModelProperty(value = "取消关注时间")
    private GenderEnum gender;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "身份")
    private String province;

    @ApiModelProperty(value = "国家")
    private String country;


}
