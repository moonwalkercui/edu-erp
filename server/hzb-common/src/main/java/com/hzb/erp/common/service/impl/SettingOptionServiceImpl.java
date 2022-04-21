package com.hzb.erp.common.service.impl;

import cn.hutool.core.date.DateException;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzb.erp.common.constants.CacheNames;
import com.hzb.erp.common.entity.SettingOption;
import com.hzb.erp.common.exception.ParamValidateException;
import com.hzb.erp.common.mapper.SettingOptionMapper;
import com.hzb.erp.common.service.SettingOptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.quartz.mapper.QuartzJobMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>
 * 系统设置表 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class SettingOptionServiceImpl extends ServiceImpl<SettingOptionMapper, SettingOption> implements SettingOptionService {

    @Autowired
    private QuartzJobMapper quartzJobMapper;

    @Override
    @Cacheable(value = CacheNames.SETTING_CACHE)
    public SettingOption getByCode(String code) {
        QueryWrapper<SettingOption> qw = new QueryWrapper<>();
        qw.eq("code", code).last("limit 1");
        return this.getOne(qw);
    }

    @Override
    public boolean valueValidate(SettingOption option) {
        boolean res = true;
        String value = option.getValue();
        String type = option.getValueType();
        String name = option.getName();
        boolean isBlank = StringUtils.isBlank(value);
        boolean valIsNum = NumberUtil.isNumber(value);

        if(isBlank) {
            throw new ParamValidateException(option.getName() + "未定义");
        }

        if (SettingServiceImpl.INT_TYPE.equals(type) && !valIsNum) {
            throw new ParamValidateException(name + "的值须是数字");
        } else if(SettingServiceImpl.BOOL_TYPE.equals(type) && !Arrays.asList(new String[]{"true", "false"}).contains(type)) {
            throw new ParamValidateException(name + "的值须是true或false");
        } else if(SettingServiceImpl.TIME_TYPE.equals(type)) {
            try {
                DateUtil.parse(value);
            } catch (DateException e) {
                throw new ParamValidateException(name + "的值须为时间");
            }
        }

        return res;
    }
}
