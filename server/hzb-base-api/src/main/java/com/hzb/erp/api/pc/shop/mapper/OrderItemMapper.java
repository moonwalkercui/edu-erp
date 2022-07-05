package com.hzb.erp.api.pc.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.api.pc.shop.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单物品
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    String getNamesByOrderId(Long orderId);

    List<OrderItem> getList(Long orderId);

    /**
    * 获取购买的课程id列表
    * */
    List<Long> getCourseIdsByOrderId(Long orderId);
}
