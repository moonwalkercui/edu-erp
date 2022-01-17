package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Setting;

/**
 * 系统设置服务
 */
public interface SettingService extends IService<Setting> {
//    /**
//    * 自动根据code更新或创建
//    * */
//    Boolean autoSave(String code, String name, String remark, Integer sortNum);

    String strValue(String code);

    Integer intValue(String code);

    Boolean boolValue(String code);

    String[] listStrValue(String code);

    int[] listIntValue(String code);

}
