package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Position;
import com.hzb.erp.exception.BizException;
import com.hzb.erp.common.mapper.PositionMapper;
import com.hzb.erp.common.pojo.CommonParamDTO;
import com.hzb.erp.common.pojo.NameValidDTO;
import com.hzb.erp.common.pojo.PositionVO;
import com.hzb.erp.common.service.PositionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {

    @Override
    public IPage<PositionVO> getList(CommonParamDTO param) {
        return baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public List<PositionVO> getAll(CommonParamDTO param) {
        return baseMapper.getList(param);
    }

    @Override
    public boolean saveOrUpdateByDTO(NameValidDTO dto) {
        Position item = new Position();
        BeanUtils.copyProperties(dto, item);

        if (item.getId() == null) {
            if (getByName(item.getName()) != null) {
                throw new BizException("该职位名称被占用");
            }
        } else {
            QueryWrapper<Position> qw = new QueryWrapper<>();
            qw.eq("name", item.getName()).ne("id", item.getId()).last("limit 1");
            if (getOne(qw) != null) {
                throw new BizException("该职位名称被占用!");
            }
        }

        return this.saveOrUpdate(item);
    }

    private Position getByName(String name) {
        QueryWrapper<Position> qw = new QueryWrapper<>();
        qw.eq("name", name).last("limit 1");
        return getOne(qw);
    }

    @Override
    public boolean handleDel(List<Long> ids) {
        return removeByIds(ids);
    }
}




