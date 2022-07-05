package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.Org;
import com.hzb.erp.common.pojo.OrgParamDTO;
import com.hzb.erp.common.pojo.OrgVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * com.xiangrui.marine.common.entity.Org
 */
@Mapper
public interface OrgMapper extends BaseMapper<Org> {

    IPage<OrgVO> getList(Page<?> page, OrgParamDTO param);

    List<OrgVO> getList(@Param("param") OrgParamDTO param);

    OrgVO getInfo(Long id);

    /**
    * 获取下级所有机构列表
    * */
    List<Org> getChildrenList(Long orgId);
}




