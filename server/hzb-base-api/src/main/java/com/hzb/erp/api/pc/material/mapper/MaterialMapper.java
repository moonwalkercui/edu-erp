package com.hzb.erp.api.pc.material.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.api.pc.material.entity.Material;
import com.hzb.erp.api.pc.material.pojo.MaterialParamDTO;
import com.hzb.erp.api.pc.material.pojo.MaterialVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.hzb.erp.api.adminCenter.material.entity.Material
 */
@Mapper
public interface MaterialMapper extends BaseMapper<Material> {
    @DataScoped
    IPage<MaterialVO> getList(Page<?> page, MaterialParamDTO param);

    @DataScoped
    List<MaterialVO> getList(@Param("param") MaterialParamDTO param);

    MaterialVO getInfo(Long id);
}




