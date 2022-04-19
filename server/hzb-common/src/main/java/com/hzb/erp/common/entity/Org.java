package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hzb.erp.common.enums.OrgLevelEnum;
import com.hzb.erp.common.enums.OrgTypeEnum;
import com.hzb.erp.service.bo.ParentListBO;
import lombok.Data;

/**
 * 机构表
 *
 * @TableName org
 */
@TableName(value = "org")
@Data
public class Org extends AutoFillEntity implements ParentListBO {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 上级公司
     */
    private Long pid;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 公司简称
     */
    private String shortname;

    /**
     * ID路径
     */
    private String idPath;

    /**
     * 级别
     */
    private OrgLevelEnum level;

    /**
     * 类型
     */
    private OrgTypeEnum type;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 传真
     */
    private String fax;

    /**
     * Email
     */
    private String email;
    private String license;

    /**
     * 是否有效
     */
    private Boolean state;

    /**
     * 描述
     */
    private String info;

    /**
     * 省
     */
    private Integer province;

    /**
     * 市
     */
    private Integer city;

    /**
     * 区
     */
    private Integer district;

    /**
     * 地址
     */
    private String address;

    /**
     * 排序
     */
    private Integer sortNum;

}