package com.hzb.erp.api.pc.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.shop.entity.Order;
import com.hzb.erp.api.pc.shop.pojo.OrderListParamDTO;
import com.hzb.erp.api.pc.shop.pojo.OrderVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    IPage<OrderVO> getList(Page<Object> objectPage, OrderListParamDTO param);
    OrderVO getInfo(Long orderId);

}




