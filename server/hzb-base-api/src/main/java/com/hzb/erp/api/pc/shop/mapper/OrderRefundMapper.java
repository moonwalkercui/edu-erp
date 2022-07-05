package com.hzb.erp.api.pc.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.shop.entity.OrderRefund;
import com.hzb.erp.api.pc.shop.pojo.OrderRefundParamDTO;
import com.hzb.erp.api.pc.shop.pojo.OrderRefundVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单退款 mapper
 */
@Mapper
public interface OrderRefundMapper extends BaseMapper<OrderRefund> {

    IPage<OrderRefundVo> getList(Page<Object> objectPage, OrderRefundParamDTO param);
    List<OrderRefundVo> getList(@Param("param") OrderRefundParamDTO param);
}
