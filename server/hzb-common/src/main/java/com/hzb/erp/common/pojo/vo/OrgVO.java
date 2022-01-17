package com.hzb.erp.common.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hzb.erp.common.enums.OrgLevelEnum;
import com.hzb.erp.common.enums.OrgTypeEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrgVO {
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
    private String provinceName;

    /**
     * 市
     */
    private Integer city;
    private String cityName;

    /**
     * 区
     */
    private Integer district;
    private String districtName;

    /**
     * 地址
     */
    private String address;

    /**
     * 排序
     */
    private Integer sortNum;

    private Boolean hasChildren;

    private List<Integer> region;

    public List<Integer> getRegion() {
        return Arrays.asList(province, city, district);
    }

}
