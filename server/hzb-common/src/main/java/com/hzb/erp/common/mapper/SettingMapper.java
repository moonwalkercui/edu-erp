package com.hzb.erp.common.mapper;

import com.hzb.erp.common.entity.Setting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.entity.SettingOption;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * generator.domain.Setting
 */
public interface SettingMapper extends BaseMapper<Setting> {

    /**
    * 根据外键setting_id获取配置列表
    * */
    List<SettingOption> listOptionByCode(@Param("code") String code);
}




