package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Setting;
import com.hzb.erp.common.entity.SettingOption;
import com.hzb.erp.common.enums.SettingCodeEnum;
import com.hzb.erp.common.mapper.SettingMapper;
import com.hzb.erp.common.service.SettingOptionService;
import com.hzb.erp.common.service.SettingService;
import com.hzb.erp.common.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {

    public final String STR_TYPE = "str";
    public final String INT_TYPE = "int";
    public final String BOOL_TYPE = "bool";
    public final String LIST_STR_TYPE = "strlist";
    public final String LIST_INT_TYPE = "intlist";

    @Resource
    private SettingOptionService settingOptionService;

    @Resource
    private SettingMapper settingMapper;

    @Override
    public Map<String, Object> listOptionByCode(SettingCodeEnum code) {
        List<SettingOption> options = settingMapper.listOptionByCode(String.valueOf(code).toLowerCase());
        Map<String, Object> res = new HashMap<>();
        for(SettingOption opt : options) {
            res.put(opt.getCode(), opt.getValue());
        }
        return res;
    }

    @Override
    public String strValue(String code) {
        SettingOption option = getOption(code);
        if (StringUtils.isBlank(option.getValue())) {
            return null;
        }
        if (!STR_TYPE.equals(option.getValueType())) {
            throw new BizException("参数类型value_type需要为str");
        }
        return option.getValue();
    }

    @Override
    public Integer intValue(String code) {
        SettingOption option = getOption(code);
        if (StringUtils.isBlank(option.getValue())) {
            return null;
        }
        if (!INT_TYPE.equals(option.getValueType())) {
            throw new BizException("参数类型value_type需要为int");
        }
        return Integer.valueOf(option.getValue());
    }

    @Override
    public Boolean boolValue(String code) {
        SettingOption option = getOption(code);
        if (StringUtils.isBlank(option.getValue())) {
            return null;
        }
        if (!BOOL_TYPE.equals(option.getValueType())) {
            throw new BizException("参数类型value_type需要为bool");
        }

        return Boolean.parseBoolean(option.getValue());
    }

    @Override
    public String[] listStrValue(String code) {
        SettingOption option = getOption(code);
        if (option.getValue() == null) {
            return null;
        }
        if (!LIST_STR_TYPE.equals(option.getValueType())) {
            throw new BizException("参数类型value_type需要为strList");
        }

        return option.getValue().split(",");
    }

    @Override
    public int[] listIntValue(String code) {
        SettingOption option = getOption(code);
        if (StringUtils.isBlank(option.getValue())) {
            return null;
        }
        if (!LIST_INT_TYPE.equals(option.getValueType())) {
            throw new BizException("参数类型value_type需要为strList");
        }
        List<String> list = Arrays.asList(option.getValue().split(","));
        return list.stream().mapToInt(Integer::valueOf).toArray();
    }

    private SettingOption getOption(String code) {
        if (StringUtils.isBlank(code)) {
            throw new BizException("配置获取方法中缺少CODE");
        }
        SettingOption option = settingOptionService.getByCode(code);
        if (option == null) {
            throw new BizException("获取配置异常");
        }
        return option;
    }
//    @Override
//    public Boolean autoSave(String code, String name, String remark, Integer sortNum) {
//        QueryWrapper<Setting> qw = new QueryWrapper<>();
//        qw.eq("code", code);
//        Setting one = this.getOne(qw);
//        if(one == null) {
//            one = new Setting();
//        }
//        one.setCode(code);
//        one.setName(name);
//        one.setRemark(remark);
//        one.setSortNum(sortNum);
//        return this.saveOrUpdate(one);
//    }
}
