package com.hzb.erp.common.service;

import com.hzb.erp.common.entity.SettingOption;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统设置表 服务类
 * </p>
 *
 * @author Ryan
 */
public interface SettingOptionService extends IService<SettingOption> {

    /**
    * 通过code获取配置项
    * */
    SettingOption getByCode(String code);

    /**
     * 值验证
     * */
    boolean valueValidate(SettingOption option);

}
