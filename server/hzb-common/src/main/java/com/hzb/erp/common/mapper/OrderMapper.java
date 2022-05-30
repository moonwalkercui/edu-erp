package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.Order;
import com.hzb.erp.common.pojo.dto.OrderListParamDTO;
import com.hzb.erp.common.pojo.vo.OrderVO;

/**
 * 订单
 */
public interface OrderMapper extends BaseMapper<Order> {

    IPage<OrderVO> getList(Page<Object> objectPage, OrderListParamDTO param);
}




