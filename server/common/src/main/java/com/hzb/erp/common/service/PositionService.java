package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Position;
import com.hzb.erp.common.pojo.dto.CommonParamDTO;
import com.hzb.erp.common.pojo.dto.NameValidDTO;
import com.hzb.erp.common.pojo.vo.PositionVO;

import java.util.List;

/**
 *
 */
public interface PositionService extends IService<Position> {
    
    IPage<PositionVO> getList(CommonParamDTO param);

    List<PositionVO> getAll(CommonParamDTO param);

    boolean saveOrUpdateByDTO(NameValidDTO dto);

    boolean handleDel(List<Long> ids);
}
