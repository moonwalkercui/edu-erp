package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hzb.erp.common.entity.CreditMall;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Material;
import com.hzb.erp.common.pojo.dto.CreditExchangeParamDTO;
import com.hzb.erp.common.pojo.dto.CreditMallParamDTO;
import com.hzb.erp.common.pojo.vo.CreditMallVO;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface CreditMallService extends IService<CreditMall> {
    IPage<CreditMallVO> getList(CreditMallParamDTO param);

    List<Map<String, Object>> getAll(String keyword);

    Boolean saveOrUpdateByDTO(CreditMall dto);

    CreditMallVO getInfo(Long id);

    boolean exchange(CreditExchangeParamDTO dto);
}
