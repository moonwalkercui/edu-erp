package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Setting;
import com.hzb.erp.common.enums.SettingCodeEnum;

import java.util.Map;

/**
 * 系统设置服务
 */
public interface SettingService extends IService<Setting> {

    /**
    * 根据code获取code下所有配置
    * */
    Map<String, Object> listOptionByCode(SettingCodeEnum code);

    String strValue(String code);

    String strValue(String code, String defaultValue);

    Integer intValue(String code);

    Boolean boolValue(String code);

    String[] listStrValue(String code);

    int[] listIntValue(String code);

}
