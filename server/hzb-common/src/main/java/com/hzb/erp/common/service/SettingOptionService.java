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

    SettingOption getByCode(String code);
}
