package com.hzb.erp.common.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.*;
import com.hzb.erp.common.enums.OrderItemTypeEnum;
import com.hzb.erp.common.enums.OrderStateEnum;
import com.hzb.erp.common.mapper.*;
import com.hzb.erp.common.pojo.dto.OrderConfirmDTO;
import com.hzb.erp.common.pojo.dto.OrderListParamDTO;
import com.hzb.erp.common.pojo.vo.OrderVo;
import com.hzb.erp.common.service.CourseService;
import com.hzb.erp.common.service.OrderService;
import com.hzb.erp.utils.RequestUtil;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private final UserMapper userMapper;
    private final CourseMapper courseMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;

    @Override
    public Order makeOrder(OrderConfirmDTO dto) {

        String sn = makeOrderSn();
        Order order = new Order();
        order.setSn(sn);
        order.setUserId(dto.getUserId());
        order.setStudentId(dto.getStudentId());
        order.setPayMoney(dto.getPrice());
        order.setRemark(dto.getRemark());
        order.setRefunded(false);
        order.setState(OrderStateEnum.UNPAID);
        this.baseMapper.insert(order);

        Course course = courseMapper.selectById(dto.getCourseId());
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setItemId(dto.getCourseId());
        orderItem.setItemName(course.getName());
        orderItem.setPrice(dto.getPrice());
        orderItem.setQuantity(course.getLessonCount());
        orderItem.setItemType(OrderItemTypeEnum.COURSE);
        orderItemMapper.insert(orderItem);

        return order;
    }

    @Override
    public void paySuccessNotify(String openid, String orderSn, String transactionId, Integer totalFee) {
        QueryWrapper<Order> qw1 = new QueryWrapper<>();
        qw1.eq("sn", orderSn);
        qw1.last("limit 1");
        Order order = this.baseMapper.selectOne(qw1);
        User user = userMapper.getByOpenid(openid);

        Payment payment = new Payment();
        if (order!=null) {
            payment.setOrderId(order.getId());
            order.setState(OrderStateEnum.PAID);
        } else {
            log.error("【！！！严重异常！！！】支付回调未找到订单Sn：" + orderSn);
        }
        if (user!=null) {
            payment.setUserId(user.getId());
        } else {
            log.error("支付回调未找到用户Openid：" + openid);
        }
        payment.setOrderSn(orderSn);
        payment.setWxOpenid(openid);
        payment.setWxTradeId(transactionId);

        totalFee = totalFee == null ? 0 : totalFee;
        payment.setPayMoney(new BigDecimal(totalFee / 100));

        paymentMapper.insert(payment);
        if (order!=null) {
            orderMapper.updateById(order);
        }
    }

    @Override
    public IPage<OrderVo> getList(OrderListParamDTO param) {
        IPage<OrderVo> records = baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
        if(records.getRecords()!=null) {
            for(OrderVo vo : records.getRecords()) {
                vo.setItemList(orderItemMapper.getList(vo.getId()));
            }
        }
        return records;
    }

    /**
    * 订单号生成规则
    * */
    private String makeOrderSn() {
        return IdUtil.simpleUUID();
    }
}
