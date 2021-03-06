package com.hzb.erp.api.pc.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.pc.course.entity.Course;
import com.hzb.erp.api.pc.course.mapper.CourseMapper;
import com.hzb.erp.api.pc.finance.entity.Payment;
import com.hzb.erp.api.pc.shop.entity.Order;
import com.hzb.erp.api.pc.shop.entity.OrderItem;
import com.hzb.erp.api.pc.shop.mapper.OrderItemMapper;
import com.hzb.erp.api.pc.shop.mapper.OrderMapper;
import com.hzb.erp.api.pc.shop.mapper.OrderRefundMapper;
import com.hzb.erp.api.pc.shop.mapper.PaymentMapper;
import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.api.pc.student.mapper.StudentMapper;
import com.hzb.erp.common.enums.OrderItemTypeEnum;
import com.hzb.erp.common.enums.OrderStateEnum;
import com.hzb.erp.common.entity.Staff;
import com.hzb.erp.common.entity.User;
import com.hzb.erp.exception.BizException;
import com.hzb.erp.common.mapper.*;
import com.hzb.erp.api.pc.shop.pojo.OrderConfirmDTO;
import com.hzb.erp.api.pc.shop.pojo.OrderListParamDTO;
import com.hzb.erp.api.pc.shop.pojo.OrderRefundParamDTO;
import com.hzb.erp.api.pc.student.pojo.StudentCourseSaveDTO;
import com.hzb.erp.api.pc.shop.pojo.OrderVO;
import com.hzb.erp.api.pc.shop.service.OrderService;
import com.hzb.erp.common.service.SettingService;
import com.hzb.erp.common.service.StaffService;
import com.hzb.erp.api.pc.student.service.StudentCourseService;
import com.hzb.erp.api.pc.sys.service.NotificationService;
import com.hzb.erp.service.enums.SettingNameEnum;
import com.hzb.erp.service.notification.NoticeCodeEnum;
import com.hzb.erp.service.notification.bo.NewOrderBO;
import com.hzb.erp.utils.DateTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * ?????? ???????????????
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
    private final NotificationService notificationService;
    private final StaffService staffService;
    private final SettingService settingService;

    @Override
    public Order makeOrder(OrderConfirmDTO dto) {
        // ????????????
        Course course = courseMapper.selectById(dto.getCourseId());
        int storage = course.getStorage();
        if(storage == 0) {
            throw new BizException("???????????????");
        }
        // ????????????
        course.setStorage(storage - 1);
        courseMapper.updateById(course);

        // ????????????
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

        // ??????????????????????????????
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setItemId(dto.getCourseId());
        orderItem.setItemName(course.getName());
        orderItem.setPrice(dto.getPrice());
        orderItem.setQuantity(course.getLessonCount() == null ? 0 : course.getLessonCount());
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
            log.error("???????????????????????????????????????????????????????????????Sn???" + orderSn);
        }
        if (user!=null) {
            payment.setUserId(user.getId());
        } else {
            log.error("???????????????????????????Openid???" + openid);
        }
        payment.setOrderSn(orderSn);
        payment.setWxOpenid(openid);
        payment.setWxTradeId(transactionId);

        BigDecimal payMoney = totalFee == null ? BigDecimal.ZERO : new BigDecimal(totalFee);
        payment.setPayMoney(payMoney.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP));
        paymentMapper.insert(payment);

        // ??????order?????????null,????????????????????????????????????????????????????????????
        if (order!=null){
            Student student = studentMapper.selectById(order.getStudentId());

            QueryWrapper<OrderItem> qwOi = new QueryWrapper<>();
            qwOi.eq("order_id", order.getId());
            List<OrderItem> orderItems = orderItemMapper.selectList(qwOi);

            String courseNames = "";

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
                    studentCourseSaveDTO.setDiscount(BigDecimal.ZERO); // ???????????? todo
                    studentCourseSaveDTO.setExpireDate(LocalDate.now().plusMonths(course.getExpireMonths() == null ? 12 : course.getExpireMonths()));
                    studentCourseSaveDTO.setStartDate(LocalDate.now());
                    studentCourseSaveDTO.setRemark(order.getRemark());
                    // ??????????????????????????????????????????????????????
                    studentCourseService.addOne(studentCourseSaveDTO, null);
                    courseNames+= course.getName();
                }
            }


            // ???????????????????????????
            String timeStr = DateTool.formatDefault(order.getPayTime());
            if(student.getCounselor() != null ) {
                Staff toStaff = staffService.getById(student.getCounselor());
                if(toStaff == null) {
                    String defaultMobile = settingService.strValue(SettingNameEnum.ORDER_NOTICE_MOBILE.getCode());
                    toStaff = staffService.getByMobile(defaultMobile);
                }
                if( toStaff != null ) {
                    NewOrderBO bo = new NewOrderBO();
                    bo.setOrderSn(order.getSn());
                    bo.setOrderTime(timeStr);
                    bo.setTeacherName(toStaff.getName());
                    bo.setContent(student.getName() + "???????????????" + courseNames + "??????????????????????????????????????????");
                    notificationService.sendToTeacher(NoticeCodeEnum.TEACHER_NEW_ORDER, bo, toStaff);
                }
            }

            NewOrderBO bo1 = new NewOrderBO();
            bo1.setOrderSn(order.getSn());
            bo1.setOrderTime(timeStr);
            bo1.setStudentName(student.getName());
            bo1.setContent("??????????????????" + courseNames + "?????????????????????????????????");
            notificationService.sendToStudent(NoticeCodeEnum.STUDENT_NEW_ORDER, bo1, student);

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
                vo.setCancelAble(checkCanRefund(vo));
            }
        }
        return records;
    }

    @Override
    public OrderVO getInfo(Long orderId) {
        OrderVO vo = this.baseMapper.getInfo(orderId);
        vo.setItemList(orderItemMapper.getList(vo.getId()));
        OrderRefundParamDTO dto = new OrderRefundParamDTO();
        dto.setOrderId(vo.getId());
        vo.setRefundList(orderRefundMapper.getList(dto));
        vo.setCancelAble(checkCanRefund(vo));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void autoCloseOrder() {

        // ?????????????????????????????????
        LocalDateTime time = LocalDateTime.now().plusMinutes(10L);
        String timeStr = DateTool.formatDefault(time);

        QueryWrapper<Order> qw = new QueryWrapper<>();
        qw.le("add_time", timeStr);
        qw.eq("state", OrderStateEnum.UNPAID.getCode());
        List<Order> orders = baseMapper.selectList(qw);

        for(Order item : orders) {
            item.setState(OrderStateEnum.CANCELED);
            item.setCancelTime(LocalDateTime.now());
        }
        this.updateBatchById(orders);

        for(Order item : orders) {
            this.afterCancelOrder(item);
        }
    }

    @Override
    public boolean cancel(Order order) {
        if(order == null) {
            throw new BizException("????????????");
        }
        if(!OrderStateEnum.UNPAID.equals(order.getState())) {
            throw new BizException("???????????????????????????");
        }
        order.setState(OrderStateEnum.CANCELED);
        order.setCancelTime(LocalDateTime.now());
        this.updateById(order);
        this.afterCancelOrder(order);
        return true;
    }

    @Override
    public boolean checkCanRefund(OrderVO ord) {
        // ?????????????????????,??????????????????????????????????????????
        if((ord.getRefunded() !=null && ord.getRefunded()) ||
                OrderStateEnum.UNPAID.equals(ord.getState()) ||
                OrderStateEnum.CANCELED.equals(ord.getState())) {
            return false;
        }
        if(ord.getPayMoney() == null || BigDecimal.ZERO.compareTo(ord.getPayMoney()) >= 0 ) {
            return false;
        }
        if( OrderStateEnum.PAID.equals(ord.getState()) || OrderStateEnum.EVALUATE.equals(ord.getState())) {
            int limitHour = settingService.intValue(SettingNameEnum.ORDER_REFUND_LIMIT_HOUR.getCode());
            if(ord.getPayTime() == null) {
                return false;
            }
            // 0???????????????
            if (limitHour== 0 || ord.getPayTime().isAfter(LocalDateTime.now().minusHours(limitHour))) {
                return true;
            }
        }
        return false;
    }

    /**
    * ????????????????????????
    * */
    private void afterCancelOrder(Order order) {
        QueryWrapper<OrderItem> qwOi = new QueryWrapper<>();
        qwOi.eq("order_id", order.getId());
        List<OrderItem> orderItems = orderItemMapper.selectList(qwOi);

        for(OrderItem item : orderItems) {
            if(OrderItemTypeEnum.COURSE.equals(item.getItemType())) {
                Course course = courseMapper.selectById(item.getItemId());
                int storage = course.getStorage();
                // ????????????
                course.setStorage(storage + item.getQuantity());
                courseMapper.updateById(course);
            }
        }

    }

}
