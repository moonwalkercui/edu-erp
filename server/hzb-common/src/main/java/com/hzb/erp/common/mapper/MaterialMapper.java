package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.Material;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.pojo.dto.MaterialParamDTO;
import com.hzb.erp.common.pojo.vo.MaterialVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.hzb.erp.common.entity.Material
 */
public interface MaterialMapper extends BaseMapper<Material> {
    @DataScoped
    IPage<MaterialVO> getList(Page<?> page, MaterialParamDTO param);

    @DataScoped
    List<MaterialVO> getList(@Param("param") MaterialParamDTO param);

    MaterialVO getInfo(Long id);
}




