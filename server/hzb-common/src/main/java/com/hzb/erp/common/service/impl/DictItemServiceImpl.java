package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Dict;
import com.hzb.erp.common.entity.DictItem;
import com.hzb.erp.exception.BizException;
import com.hzb.erp.common.mapper.DictItemMapper;
import com.hzb.erp.common.service.DictItemService;
import com.hzb.erp.common.service.DictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统设置表 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService {

    @Autowired
    private DictService dictService;

    @Override
    public void inexistThenInsert(String code, String name) {
        if (StringUtils.isBlank(name)) {
            return;
        }
        Dict dict = dictService.getByCode(code);
        if (dict == null) {
            throw new BizException("code不存在");
        }
        QueryWrapper<DictItem> qw = new QueryWrapper<>();
        qw.eq("dict_id", dict.getId()).eq("name", name).last("limit 1");
        DictItem find = getOne(qw);
        if (find == null) {
            DictItem p = new DictItem();
            p.setDictId(dict.getId());
            p.setName(name);
            save(p);
        }
    }

    @Override
    public String getNameById(Long id) {
        DictItem item = this.baseMapper.selectById(id);
        if(item == null) {
            return "";
        }
        return item.getName();
    }
}
