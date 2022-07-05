package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.Position;
import com.hzb.erp.common.pojo.CommonParamDTO;
import com.hzb.erp.common.pojo.PositionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * com.xiangrui.marine.common.entity.Position
 */
@Mapper
public interface PositionMapper extends BaseMapper<Position> {
    IPage<PositionVO> getList(Page<?> page, CommonParamDTO param);

    List<PositionVO> getList(@Param("param") CommonParamDTO param);
}




