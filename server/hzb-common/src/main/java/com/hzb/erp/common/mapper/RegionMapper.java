package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.entity.Region;

import java.util.List;

/**
 * com.xiangrui.marine.common.entity.Region
 */
public interface RegionMapper extends BaseMapper<Region> {

    List<Region> getByPid(Integer pid);
}




