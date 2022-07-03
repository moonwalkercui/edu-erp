package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.CreditExchange;
import com.hzb.erp.common.pojo.dto.CreditExchangeParamDTO;
import com.hzb.erp.common.pojo.vo.CreditExchangeVO;

/**
 * 积分兑换记录
 * @Entity com.hzb.erp.common.entity.CreditExchange
 */
public interface CreditExchangeMapper extends BaseMapper<CreditExchange> {

    IPage<CreditExchangeVO> getList(Page<Object> objectPage, CreditExchangeParamDTO param);
}




