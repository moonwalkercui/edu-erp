package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.enums.OprationTypeEnum;
import com.hzb.erp.service.ImportExportService;
import com.hzb.erp.service.NotificationService;
import com.hzb.erp.service.notification.NoticeCodeEnum;
import com.hzb.erp.service.notification.bo.NewContractBO;
import com.hzb.erp.common.entity.*;
import com.hzb.erp.common.enums.FinanceStateEnum;
import com.hzb.erp.common.enums.FinanceTypeEnum;
import com.hzb.erp.common.enums.VerifyStateEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.FinanceRecordMapper;
import com.hzb.erp.common.pojo.dto.FinanceParamDTO;
import com.hzb.erp.common.pojo.vo.FinanceRecordVO;
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
    private StudentCourseService studentCourseService;
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

    @Override
    public FinanceRecord addOne(FinanceRecord record) {
        record.setAddTime(LocalDateTime.now());
        record.setVerifyState(FinanceStateEnum.APPLY);
        this.save(record);
        return record;
    }

    @Override
    public IPage<FinanceRecordVO> getList(FinanceParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    @Transactional
    public Boolean changeState(List<Long> ids, FinanceStateEnum state, String remark, Long staffId) {
        List<FinanceRecord> list = this.listByIds(ids);
        for (FinanceRecord item : list) {
            if(!FinanceStateEnum.APPLY.equals(item.getVerifyState()) ) {
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
            handleCourseVerify(item, state);
            operationRecordService.addOne(item.getItemId(), OprationTypeEnum.StudentCourse, state.getDist(), "报单认款审核:" + state.getDist(), staffId);
        } else if (FinanceTypeEnum.REFUND.equals(item.getItemType())) {
            handleRefundVerify(item, state);
            operationRecordService.addOne(item.getItemId(), OprationTypeEnum.Refund, state.getDist(), "退课退课审核:" + state.getDist(), staffId);
        } else if (FinanceTypeEnum.SUPPLEMENT.equals(item.getItemType())) {
            handleSupplementVerify(item, state);
            operationRecordService.addOne(item.getItemId(), OprationTypeEnum.StudentCourse, state.getDist(), "缴纳欠费认款:" + state.getDist(), staffId);
        }
    }

    /**
     * 处理报单课程逻辑
     */
    private void handleCourseVerify(FinanceRecord item, FinanceStateEnum state) {

        StudentCourse target = studentCourseService.getById(item.getItemId());
        if (target == null) {
            throw new BizException("系统异常:缺少数据源!");
        }
        target.setVerifyState(state);
        studentCourseService.updateById(target);

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

            StudentCourse sc = studentCourseService.getById(refund.getStudentCourseId());
            if (sc == null) {
                throw new BizException("系统异常:缺少数据源!!!");
            }

            // 学生课程的支付金额增加, 退费课次减少: 逆向方法:  refundService.saveOrUpdateByDTO
            sc.setPaidAmount(sc.getPaidAmount().add(refund.getRefundAmount().abs()));
            sc.setCountLessonRefund(sc.getCountLessonRefund() - refund.getRefundLessonCount());
            studentCourseService.updateById(sc);

        }

        // 更新退款状态
        refund.setVerifyState(EnumTools.getByCode(state.getCode(), VerifyStateEnum.class));
        refundService.updateById(refund);


    }

    /**
     * 处理续费逻辑 暂无
     */
    private void handleSupplementVerify(FinanceRecord item, FinanceStateEnum state) {
        if (FinanceStateEnum.PASS.equals(state)) {
            // 通过
        } else {
            // 驳回
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
            put("verifyState", "认款状态");
            put("verifyStaffName", "认款人");
            put("verifyTime", "认款时间");
            put("verifyRemark", "认款备注");
        }};
        importExportService.exportExcel(header, list, "款项记录表");
    }

}
