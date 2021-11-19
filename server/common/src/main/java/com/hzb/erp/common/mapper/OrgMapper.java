package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.Org;
import com.hzb.erp.common.pojo.dto.OrgParamDTO;
import com.hzb.erp.common.pojo.vo.OrgVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  com.xiangrui.marine.common.entity.Org
 */
public interface OrgMapper extends BaseMapper<Org> {

    IPage<OrgVO> getList(Page<?> page, OrgParamDTO param);
    List<OrgVO> getList(@Param("param") OrgParamDTO param);

    OrgVO getInfo(Long id);
}




