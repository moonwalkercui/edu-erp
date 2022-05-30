package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.*;
import com.hzb.erp.common.enums.OrderItemTypeEnum;
import com.hzb.erp.common.enums.OrderStateEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.*;
import com.hzb.erp.common.pojo.dto.OrderConfirmDTO;
import com.hzb.erp.common.pojo.dto.OrderListParamDTO;
import com.hzb.erp.common.pojo.dto.OrderRefundParamDTO;
import com.hzb.erp.common.pojo.dto.StudentCourseSaveDTO;
import com.hzb.erp.common.pojo.vo.OrderVO;
import com.hzb.erp.common.service.OrderService;
import com.hzb.erp.common.service.StudentCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private final OrderRefundMapper orderRefundMapper;
    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;
    private final StudentMapper studentMapper;
    private final StudentCourseService studentCourseService;

    @Override
    public Order makeOrder(OrderConfirmDTO dto) {
        // 减少库存
        Course course = courseMapper.selectById(dto.getCourseId());
        int storage = course.getStorage();
        if(storage == 0) {
            throw new BizException("已没有名额");
        }
        // 减少名额
        course.setStorage(storage - 1);
        courseMapper.updateById(course);

        // 创建订单
        String sn = OrderService.makeOrderSn();
        Order order = new Order();
        order.setSn(sn);
        order.setUserId(dto.getUserId());
        order.setStudentId(dto.getStudentId());
        order.setOrderMoney(dto.getPrice());
        order.setRemark(dto.getRemark());
        order.setRefunded(false);
        order.setState(OrderStateEnum.UNPAID);
        this.baseMapper.insert(order);

        // 创建订单购买内容记录
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setItemId(dto.getCourseId());
        orderItem.setItemName(course.getName());
        orderItem.setPrice(dto.getPrice());
        orderItem.setQuantity(course.getLessonCount());
        orderItem.setCover(course.getCover());
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
            order.setPayTime(LocalDateTime.now());
            orderMapper.updateById(order);
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

        BigDecimal payMoney = totalFee == null ? BigDecimal.ZERO : new BigDecimal(totalFee);
        payment.setPayMoney(payMoney.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP));
        paymentMapper.insert(payment);

        // 其实order不会是null,但是作为微信支付回调，最大可能的记录一下
        if (order!=null){
            Student student = studentMapper.selectById(order.getStudentId());

            QueryWrapper<OrderItem> qwOi = new QueryWrapper<>();
            qwOi.eq("order_id", order.getId());
            List<OrderItem> orderItems = orderItemMapper.selectList(qwOi);

            for(OrderItem item : orderItems) {
                if(OrderItemTypeEnum.COURSE.equals(item.getItemType())) {
                    Course course = courseMapper.selectById(item.getItemId());
                    StudentCourseSaveDTO studentCourseSaveDTO = new StudentCourseSaveDTO();
                    studentCourseSaveDTO.setStudentId(order.getStudentId());
                    studentCourseSaveDTO.setStudentName(student.getName());
                    studentCourseSaveDTO.setCourseId(item.getItemId());
                    studentCourseSaveDTO.setCourseAmount(item.getPrice());
                    studentCourseSaveDTO.setPaidAmount(item.getPrice());
                    studentCourseSaveDTO.setCountLessonTotal(item.getQuantity());
                    studentCourseSaveDTO.setDiscount(BigDecimal.ZERO); // 打折金额 todo
                    studentCourseSaveDTO.setExpireDate(LocalDate.now().plusMonths(course.getExpireMonths() == null ? 12 : course.getExpireMonths()));
                    studentCourseSaveDTO.setStartDate(LocalDate.now());
                    studentCourseSaveDTO.setRemark(order.getRemark());
                    // 创建学生课程中间数据，并记录财务数据
                    studentCourseService.addOne(studentCourseSaveDTO, null);
                }
            }

        }
    }

    @Override
    public IPage<OrderVO> getList(OrderListParamDTO param) {
        IPage<OrderVO> records = baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
        if(records.getRecords()!=null) {
            for(OrderVO vo : records.getRecords()) {
                vo.setItemList(orderItemMapper.getList(vo.getId()));
                OrderRefundParamDTO dto = new OrderRefundParamDTO();
                dto.setOrderId(vo.getId());
                vo.setRefundList(orderRefundMapper.getList(dto));
            }
        }
        return records;
    }

}
