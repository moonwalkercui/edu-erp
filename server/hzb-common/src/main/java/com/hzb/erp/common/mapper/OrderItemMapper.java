package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.entity.OrderItem;

import java.util.List;

/**
 * 订单物品
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    String getNamesByOrderId(Long orderId);

    List<OrderItem> getList(Long orderId);

    /**
    * 获取购买的课程id列表
    * */
    List<Long> getCourseIdsByOrderId(Long orderId);
}
