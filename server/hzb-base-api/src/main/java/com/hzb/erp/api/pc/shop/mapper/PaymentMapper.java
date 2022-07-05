package com.hzb.erp.api.pc.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.api.pc.finance.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付记录
 */
@Mapper
public interface PaymentMapper extends BaseMapper<Payment> {

}
