package com.hzb.erp.common.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Order;
import com.hzb.erp.common.enums.OrderStateEnum;
import com.hzb.erp.common.mapper.OrderMapper;
import com.hzb.erp.common.pojo.dto.OrderConfirmDTO;
import com.hzb.erp.common.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public Order makeOrder(OrderConfirmDTO dto) {

        String sn = makeOrderSn();

        Order order = new Order();
        order.setSn(sn);
        order.setCourseId(dto.getCourseId());
        order.setUserId(dto.getUserId());
        order.setStudentId(dto.getStudentId());
        order.setPayMoney(dto.getPrice());
        order.setRemark(dto.getRemark());
        order.setRefunded(false);
        order.setState(OrderStateEnum.UNPAID);

        this.baseMapper.insert(order);

        return order;
    }

    /**
    * 订单号生成规则
    * */
    private String makeOrderSn() {
        return IdUtil.simpleUUID();
    }
}
