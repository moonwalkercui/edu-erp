package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.CreditExchange;
import com.hzb.erp.common.pojo.dto.AuditParamDTO;
import com.hzb.erp.common.pojo.dto.CreditExchangeParamDTO;
import com.hzb.erp.common.pojo.vo.CreditExchangeVO;

/**
 *
 */
public interface CreditExchangeService extends IService<CreditExchange> {

    /**
     * 分页
     * */
    IPage<CreditExchangeVO> getList(CreditExchangeParamDTO param);


    /**
     * 审核
     * */
    boolean exchangeAudit(AuditParamDTO auditDTO);
}
