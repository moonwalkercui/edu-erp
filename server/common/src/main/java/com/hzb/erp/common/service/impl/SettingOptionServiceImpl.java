package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzb.erp.common.entity.SettingOption;
import com.hzb.erp.common.mapper.SettingOptionMapper;
import com.hzb.erp.common.service.SettingOptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统设置表 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class SettingOptionServiceImpl extends ServiceImpl<SettingOptionMapper, SettingOption> implements SettingOptionService {

    @Override
    public SettingOption getByCode(String code) {
        QueryWrapper<SettingOption> qw = new QueryWrapper<>();
        qw.eq("code", code).last("limit 1");
        return this.getOne(qw);
    }
}
