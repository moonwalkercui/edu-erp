package com.hzb.erp.api.pc.creditMall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.creditMall.entity.CreditExchange;
import com.hzb.erp.common.pojo.AuditParamDTO;
import com.hzb.erp.api.pc.creditMall.pojo.CreditExchangeParamDTO;
import com.hzb.erp.api.pc.creditMall.pojo.CreditExchangeVO;

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
