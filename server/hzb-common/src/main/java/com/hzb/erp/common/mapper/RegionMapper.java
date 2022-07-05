package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.entity.Region;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * com.xiangrui.marine.common.entity.Region
 */
@Mapper
public interface RegionMapper extends BaseMapper<Region> {

    List<Region> getByPid(Integer pid);
}




