package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Order;
import com.hzb.erp.common.entity.OrderRefund;
import com.hzb.erp.common.enums.OrderRefundStateEnum;
import com.hzb.erp.common.enums.OrderStateEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.OrderMapper;
import com.hzb.erp.common.mapper.OrderRefundMapper;
import com.hzb.erp.common.pojo.dto.IdsAndContentDTO;
import com.hzb.erp.common.pojo.dto.OrderRefundDTO;
import com.hzb.erp.common.pojo.dto.OrderRefundParamDTO;
import com.hzb.erp.common.pojo.vo.OrderRefundVo;
import com.hzb.erp.common.service.OrderRefundService;
import com.hzb.erp.common.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 订单退款 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
@RequiredArgsConstructor
public class OrderRefundServiceImpl extends ServiceImpl<OrderRefundMapper, OrderRefund> implements OrderRefundService {

    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public Boolean handleRefund(OrderRefundDTO dto) {

        Order order = orderMapper.selectById(dto.getOrderId());
        if(order == null) {
            throw new BizException("未查询到订单");
        }
        if(dto.getRefundMoney().compareTo(order.getPayMoney()) > 0) {
            throw new BizException("退款金额有误");
        }

        // 创建退款记录
        String refundSn = OrderService.makeOrderSn();
        OrderRefund orderRefund = new OrderRefund();
        orderRefund.setRefundSn(refundSn);
        orderRefund.setOrderId(dto.getOrderId());
        orderRefund.setUserId(dto.getUserId());
        orderRefund.setRefundMoney(dto.getRefundMoney());
        orderRefund.setReasonType(dto.getReasonType());
        orderRefund.setReason(dto.getReason());
        orderRefund.setState(OrderRefundStateEnum.APPLY);
        orderRefund.setAddTime(LocalDateTime.now());
        this.baseMapper.insert(orderRefund);

        // 更改订单状态
        order.setState(OrderStateEnum.CANCELED);
        order.setRefunded(true);
        orderMapper.updateById(order);

        return false;
    }

    @Override
    public IPage<OrderRefundVo> getList(OrderRefundParamDTO param) {
        IPage<OrderRefundVo> records = baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
        return records;
    }

    @Override
    public Boolean handleApprove(IdsAndContentDTO dto, Long staffId) {
        return false;
    }

    @Override
    public Boolean handleReject(IdsAndContentDTO dto, Long staffId) {
        return false;
    }

}
