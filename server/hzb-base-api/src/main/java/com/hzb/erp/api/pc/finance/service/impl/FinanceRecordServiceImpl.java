package com.hzb.erp.api.pc.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.pc.course.entity.Course;
import com.hzb.erp.api.pc.course.service.CourseService;
import com.hzb.erp.api.pc.finance.entity.FinanceRecord;
import com.hzb.erp.api.pc.finance.entity.Refund;
import com.hzb.erp.api.pc.finance.service.FinanceRecordService;
import com.hzb.erp.api.pc.finance.service.RefundService;
import com.hzb.erp.api.pc.shop.entity.Order;
import com.hzb.erp.api.pc.shop.entity.OrderRefund;
import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.api.pc.student.entity.StudentCourse;
import com.hzb.erp.api.pc.student.service.StudentLessonCountLogService;
import com.hzb.erp.api.pc.student.service.StudentService;
import com.hzb.erp.common.enums.FinanceStateEnum;
import com.hzb.erp.common.enums.FinanceTypeEnum;
import com.hzb.erp.common.enums.OrderRefundStateEnum;
import com.hzb.erp.common.enums.*;
import com.hzb.erp.api.pc.shop.mapper.OrderMapper;
import com.hzb.erp.api.pc.shop.mapper.OrderRefundMapper;
import com.hzb.erp.api.pc.student.mapper.StudentCourseMapper;
import com.hzb.erp.service.ImportExportService;
import com.hzb.erp.api.pc.sys.service.NotificationService;
import com.hzb.erp.service.notification.NoticeCodeEnum;
import com.hzb.erp.service.notification.bo.NewContractBO;
import com.hzb.erp.exception.BizException;
import com.hzb.erp.api.pc.finance.mapper.FinanceRecordMapper;
import com.hzb.erp.api.pc.finance.pojo.FinanceParamDTO;
import com.hzb.erp.api.pc.finance.pojo.FinanceRecordVO;
import com.hzb.erp.common.service.*;
import com.hzb.erp.utils.EnumTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 * 财务记录表 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class FinanceRecordServiceImpl extends ServiceImpl<FinanceRecordMapper, FinanceRecord> implements FinanceRecordService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentLessonCountLogService studentLessonCountLogService;
    @Autowired
    private ImportExportService importExportService;
    @Autowired
    private OperationRecordService operationRecordService;
    @Autowired
    private OrderRefundMapper orderRefundMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public IPage<FinanceRecordVO> getList(FinanceParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changeState(List<Long> ids, FinanceStateEnum state, String remark, Long staffId) {
        List<FinanceRecord> list = this.listByIds(ids);
        for (FinanceRecord item : list) {
            if (!FinanceStateEnum.APPLY.equals(item.getVerifyState())) {
                throw new BizException("已审核的项目无法重复审核");
            }
            item.setVerifyState(state);
            item.setVerifyStaff(staffId);
            item.setVerifyRemark(remark);
            item.setVerifyTime(LocalDateTime.now());
        }
        this.updateBatchById(list);

        // 审核后逻辑
        for (FinanceRecord item : list) {
            transactionAfterChangeState(item, state, staffId);
        }

        return true;
    }

    /**
     * 处理认款逻辑
     */
    private void transactionAfterChangeState(FinanceRecord item, FinanceStateEnum state, Long staffId) {

        if (FinanceTypeEnum.COURSE.equals(item.getItemType())) {
            // 课程报名审核处理
            handleCourseVerify(item, state);
            operationRecordService.addOne(item.getItemId(), OprationTypeEnum.StudentCourse, state.getDist(), "报名认款审核:" + state.getDist(), staffId);

        } else if (FinanceTypeEnum.REFUND.equals(item.getItemType())) {
            // 课程退款审核处理
            handleRefundVerify(item, state);
            operationRecordService.addOne(item.getItemId(), OprationTypeEnum.Refund, state.getDist(), "退课退课审核:" + state.getDist(), staffId);

        } else if (FinanceTypeEnum.SUPPLEMENT.equals(item.getItemType())) {
            // 课费补缴审核处理
            handleSupplementVerify(item, state);
            operationRecordService.addOne(item.getItemId(), OprationTypeEnum.StudentCourse, state.getDist(), "缴纳欠费认款:" + state.getDist(), staffId);

        } else if (FinanceTypeEnum.ORDER.equals(item.getItemType())) {
            // 购课订单
            handleOrderVerify(item, state);
            operationRecordService.addOne(item.getItemId(), OprationTypeEnum.ORDER, state.getDist(), "在线购课认款:" + state.getDist(), staffId);

        } else if (FinanceTypeEnum.ORDER_REFUND.equals(item.getItemType())) {
            // 购课订单退款
            handleOrderRefundVerify(item, state, staffId);
            operationRecordService.addOne(item.getItemId(), OprationTypeEnum.ORDER_REFUND, state.getDist(), "订单退款审核:" + state.getDist(), staffId);
        }
    }

    /**
     * 处理报名课程逻辑
     */
    private void handleCourseVerify(FinanceRecord item, FinanceStateEnum state) {

        StudentCourse target = studentCourseMapper.selectById(item.getItemId());
        if (target == null) {
            throw new BizException("系统异常:缺少数据源!");
        }
        target.setVerifyState(state);
        studentCourseMapper.updateById(target);

        if (FinanceStateEnum.PASS.equals(state)) {
            sendNewContractNotice(target);
        }

    }

    /**
     * 发送报名消息给学员
     */
    private void sendNewContractNotice(StudentCourse studentCourse) {

        Course course = courseService.getById(studentCourse.getCourseId());
        Student student = studentService.getById(studentCourse.getStudentId());

        NewContractBO bo = new NewContractBO();
        bo.setCourseName(course.getName());
        bo.setLessonCount(String.valueOf(studentCourse.getCountLessonTotal()));
        bo.setAmount(String.valueOf(studentCourse.getAmount()));
        bo.setExpireDate(String.valueOf(studentCourse.getExpireDate()));
        bo.setStartDate(String.valueOf(studentCourse.getStartDate()));
        bo.setStudentName(student.getName());
        bo.setSubject("报名成功通知");
        bo.setContent(student.getName() + "你好，恭喜你成功报名课程：" + course.getName());
        notificationService.sendToStudent(NoticeCodeEnum.STUDENT_NEW_CONTRACT, bo, student);

    }

    /**
     * 处理退款逻辑
     */
    private void handleRefundVerify(FinanceRecord item, FinanceStateEnum state) {
        Refund refund = refundService.getById(item.getItemId());

        if (FinanceStateEnum.REJECT.equals(state)) {
            // 驳回处理
            if (refund == null) {
                throw new BizException("系统异常:缺少数据源!!");
            }

            StudentCourse sc = studentCourseMapper.selectById(refund.getStudentCourseId());
            if (sc == null) {
                throw new BizException("系统异常:缺少数据源!!!");
            }

            // 学生课程的支付金额增加, 退费课次减少: 逆向方法:  refundService.saveOrUpdateByDTO
            sc.setPaidAmount(sc.getPaidAmount().add(refund.getRefundAmount().abs()));
            sc.setCountLessonRefund(sc.getCountLessonRefund() - refund.getRefundLessonCount());
            studentCourseMapper.updateById(sc);

        }

        // 更新退款状态
        refund.setVerifyState(EnumTools.getByCode(state.getCode(), VerifyStateEnum.class));
        refundService.updateById(refund);


    }

    /**
     * 处理续费逻辑 暂无 todo
     */
    private void handleSupplementVerify(FinanceRecord item, FinanceStateEnum state) {
        if (FinanceStateEnum.PASS.equals(state)) {
            // 通过
        } else {
            // 驳回
        }
    }

    /**
     * 购课订单
     */
    private void handleOrderVerify(FinanceRecord item, FinanceStateEnum state) {
        if (FinanceStateEnum.PASS.equals(state)) {
            // 订单款项审核通过后 什么也不做
        } else {
            // 订单款项驳回 微信支付完的，不允许驳回。如果需要退款的，需要学生端端操作退款
            throw new BizException("微信已支付订单无法驳回");
        }
    }

    /**
     * 购课订单退款
     */
    private void handleOrderRefundVerify(FinanceRecord item, FinanceStateEnum state, Long staffId) {
        OrderRefund orderRefund = orderRefundMapper.selectById(item.getItemId());
        boolean closeOrderRefund = false;
        if (FinanceStateEnum.PASS.equals(state)) {
            // 通过后，就可以在订单退款管理里操作退款了
            orderRefund.setState(OrderRefundStateEnum.PASS);
        } else {
            // 驳回后 用户的订单状态改成未退款状态 申请退款见 com.hzb.erp.api.adminCenter.shop.service.impl.OrderRefundServiceImpl.handleRefund
            orderRefund.setState(OrderRefundStateEnum.REJECT);
            closeOrderRefund = true;
        }
        orderRefund.setVerifyStaff(staffId);
        orderRefund.setVerifyRemark(item.getRemark());
        orderRefund.setVerifyTime(LocalDateTime.now());
        orderRefundMapper.updateById(orderRefund);

        if(closeOrderRefund) {
            Order order = orderMapper.selectById(orderRefund.getOrderId());
            order.setRefunded(false);
            orderMapper.updateById(order);
        }

    }
    /**
     * 课时变动日志
     */
    @Override
    public void makeLessonCountLog(List<Long> ids, FinanceStateEnum state, Long staffId) {
        List<FinanceRecord> list = this.listByIds(ids);
        for (FinanceRecord item : list) {
            // 退款驳回后增加课时
            if (FinanceTypeEnum.REFUND.equals(item.getItemType()) && FinanceStateEnum.REJECT.equals(state)) {
                Refund refund = refundService.getById(item.getItemId());
                studentLessonCountLogService.addOneByRefundReject(refund.getStudentId(), refund.getStudentCourseId(), Math.abs(refund.getRefundLessonCount()), staffId);
            }
        }
    }

    @Override
    public void exportExcel(FinanceParamDTO param) {
        List<FinanceRecordVO> list = this.baseMapper.getList(param);
        LinkedHashMap<String, String> header = new LinkedHashMap<String, String>() {{
            put("id", "编码");
            put("itemType", "款项类型");
            put("title", "标题");
            put("amount", "金额");
            put("payerName", "付款人");
            put("operatorName", "经手人");
            put("remark", "备注说明");
            put("addTime", "提交时间");
            put("verifyState", "审核状态");
            put("verifyStaffName", "审核人");
            put("verifyTime", "审核时间");
            put("verifyRemark", "审核备注");
        }};
        importExportService.exportExcel(header, list, "款项记录表");
    }

}
