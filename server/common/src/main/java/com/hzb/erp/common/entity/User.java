package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 家长用户实体类
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 家长姓名
     */
    private String name;
    private String email;

    /**
     * 微信id
     */
    private Long wxAccessId;

    /**
     * 手机号账号
     */
    private String mobile;

    /**
     * 账号状态
     */
    private Boolean state;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录次数
     */
    private Integer loginTimes;

    /**
     * 上次登录时间
     */
    private LocalDateTime latestLoginTime;

    /**
     * 上次登录ip
     */
    private String latestLoginIp;

    /**
     * 加入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}