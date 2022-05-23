package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.entity.OrderItem;

/**
 * 订单物品
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    String getNamesByOrderId(Long orderId);
}
