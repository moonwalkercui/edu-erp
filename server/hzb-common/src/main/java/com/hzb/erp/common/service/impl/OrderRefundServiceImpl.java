package com.hzb.erp.common.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Course;
import com.hzb.erp.common.entity.Order;
import com.hzb.erp.common.entity.OrderItem;
import com.hzb.erp.common.entity.OrderRefund;
import com.hzb.erp.common.enums.OrderItemTypeEnum;
import com.hzb.erp.common.enums.OrderRefundStateEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.CourseMapper;
import com.hzb.erp.common.mapper.OrderItemMapper;
import com.hzb.erp.common.mapper.OrderMapper;
import com.hzb.erp.common.mapper.OrderRefundMapper;
import com.hzb.erp.common.pojo.dto.OrderRefundDTO;
import com.hzb.erp.common.pojo.dto.OrderRefundParamDTO;
import com.hzb.erp.common.pojo.vo.OrderRefundVo;
import com.hzb.erp.common.service.OrderRefundService;
import com.hzb.erp.common.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单退款 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderRefundServiceImpl extends ServiceImpl<OrderRefundMapper, OrderRefund> implements OrderRefundService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final CourseMapper courseMapper;

    @Override
    @Transactional
    public Boolean handleRefund(OrderRefundDTO dto) {

        Order order = orderMapper.selectById(dto.getOrderId());
        if(order == null) {
            throw new BizException("未查询到订单");
        }
        if(BooleanUtil.isTrue(order.getRefunded())) {
            throw new BizException("已退款的订单无法重复退款");
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
        orderRefund.setStudentId(dto.getStudentId());
        orderRefund.setRefundMoney(dto.getRefundMoney());
        orderRefund.setReasonType(dto.getReasonType());
        orderRefund.setReason(dto.getReason());
        orderRefund.setState(OrderRefundStateEnum.APPLY);
        orderRefund.setAddTime(LocalDateTime.now());
        orderRefund.setItemNames(orderItemMapper.getNamesByOrderId(order.getId()));
        this.baseMapper.insert(orderRefund);

        // 更改订单状态
        order.setRefunded(true);
        orderMapper.updateById(order);

        return false;
    }

    @Override
    public IPage<OrderRefundVo> getList(OrderRefundParamDTO param) {
        IPage<OrderRefundVo> records = baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
        return records;
    }
    // 退款审核放财务模块了
//    @Override
//    @Transactional
//    public List<OrderRefund> handleApprove(IdsAndContentDTO dto, Long staffId) {
//        List<OrderRefund> list = this.listByIds(dto.getIds());
//        List<OrderRefund> handleList = new ArrayList<>();
//        for (OrderRefund item : list) {
//            if (OrderRefundStateEnum.APPLY.equals(item.getState())) {
//                // 这里先不修改退款状态，在退款回调里做更准确，只更新审核人
//                item.setVerifyId(staffId);
//                item.setVerifyRemark(dto.getContent());
//                item.setVerifyTime(LocalDateTime.now());
//                handleList.add(item);
//            }
//        }
//        if ( handleList.size() == 0 ) {
//            throw new BizException("没有可审核的条目");
//        }
//        updateBatchById(handleList);
//        return handleList;
//    }

// 退款审核放财务模块了
//    @Override
//    @Transactional
//    public Boolean handleReject(IdsAndContentDTO dto, Long staffId) {
//        List<OrderRefund> list = this.listByIds(dto.getIds());
//        List<OrderRefund> handleList = new ArrayList<>();
//        for (OrderRefund item : list) {
//            if (OrderRefundStateEnum.APPLY.equals(item.getState())) {
//                item.setState(OrderRefundStateEnum.FAIL);
//                item.setVerifyId(staffId);
//                item.setVerifyRemark(dto.getContent());
//                item.setVerifyTime(LocalDateTime.now());
//                handleList.add(item);
//            }
//        }
//        if ( handleList.size() == 0 ) {
//            throw new BizException("没有可审核的条目");
//        }
//        for (OrderRefund item : handleList) {
//            Order order = orderMapper.selectById(item.getOrderId());
//            order.setRefunded(false);
//            orderMapper.updateById(order);
//        }
//        return updateBatchById(handleList);
//    }

    @Override
    public void refundSuccessNotify(String refundSn, String tradeNo, BigDecimal refundMoney) {
        QueryWrapper<OrderRefund> qw = new QueryWrapper<>();
        qw.eq("refund_sn", refundSn);
        OrderRefund orderRefund = baseMapper.selectOne(qw);
        orderRefund.setState(OrderRefundStateEnum.PASS);
        orderRefund.setVerifyTime(LocalDateTime.now());
        this.baseMapper.updateById(orderRefund);

        List<OrderItem> itemList = orderItemMapper.getList(orderRefund.getOrderId());

        for(OrderItem item : itemList) {
            // 恢复课程的库存
            if(OrderItemTypeEnum.COURSE.equals(item.getItemType())) {
                Course course = courseMapper.selectById(item.getItemId());
                int storage = course.getStorage() == null ? 0 : course.getStorage();
                course.setStorage(storage + 1);
                courseMapper.updateById(course);
            }
        }

    }
}
