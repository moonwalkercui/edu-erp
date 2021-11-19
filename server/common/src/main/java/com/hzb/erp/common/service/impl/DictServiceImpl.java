package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzb.erp.common.entity.Dict;
import com.hzb.erp.common.mapper.DictMapper;
import com.hzb.erp.common.service.DictService;
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
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public Dict getByCode(String code) {
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        qw.eq("code", code).last("limit 1");
        return getOne(qw);
    }
}
