package com.hzb.erp.api.pc.creditMall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.creditMall.entity.CreditExchange;
import com.hzb.erp.api.pc.creditMall.pojo.CreditExchangeParamDTO;
import com.hzb.erp.api.pc.creditMall.pojo.CreditExchangeVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分兑换记录
 * @Entity com.hzb.erp.api.adminCenter.creditMall.entity.CreditExchange
 */
@Mapper
public interface CreditExchangeMapper extends BaseMapper<CreditExchange> {

    IPage<CreditExchangeVO> getList(Page<Object> objectPage, CreditExchangeParamDTO param);
}




