package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Material;
import com.hzb.erp.common.pojo.dto.MaterialParamDTO;
import com.hzb.erp.common.pojo.vo.MaterialVO;

import java.util.List;

/**
 *
 */
public interface MaterialService extends IService<Material> {

    IPage<MaterialVO> getList(MaterialParamDTO param);

    List<MaterialVO> getAll(MaterialParamDTO param);

    Boolean saveOrUpdateByDTO(Material dto);

    MaterialVO getInfo(Long id);
}
