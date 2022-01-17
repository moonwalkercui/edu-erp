package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.*;
import com.hzb.erp.common.enums.FinanceTypeEnum;
import com.hzb.erp.common.enums.RefundTypeEnum;
import com.hzb.erp.common.enums.VerifyStateEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.RefundMapper;
import com.hzb.erp.common.pojo.dto.RefundSaveDTO;
import com.hzb.erp.common.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 学员退款记录 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class RefundServiceImpl extends ServiceImpl<RefundMapper, Refund> implements RefundService {

    @Autowired
    private StudentCourseService studentCourseService;
    @Autowired
    private FinanceRecordService financeRecordService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdateByDTO(RefundSaveDTO dto, Long staffId) {

        BigDecimal amount = dto.getRefundAmount().abs();

        StudentCourse sc = studentCourseService.getById(dto.getStudentCourseId());
        if (sc == null) {
            throw new BizException("未查询到报单记录");
        }
        Refund findRecord = getOneByStudentCourseId(dto.getStudentCourseId());
        if (findRecord != null && !VerifyStateEnum.REJECT.equals(findRecord.getVerifyState())) {
            throw new BizException("不能重复发起退款"); // 驳回的可以再发起
        }
        if (amount.compareTo(sc.getPaidAmount()) > 0) {
            throw new BizException("退款金额不能大于已收金额");
        }

        if (dto.getRefundLessonCount() > studentCourseService.getLessonRemainingCount(sc)) {
            throw new BizException("退课次数不能大于剩余次数");
        }
        Course course = courseService.getById(sc.getCourseId());
        Student student = studentService.getById(sc.getStudentId());

        Refund item = new Refund();
        BeanUtils.copyProperties(dto, item);

        item.setRefundAmount(amount.negate());
        item.setTypeNum(RefundTypeEnum.COURSE);
        item.setApplyTime(LocalDateTime.now());
        item.setOperator(staffId);
        item.setVerifyState(VerifyStateEnum.APPLY);
        this.saveOrUpdate(item);

        FinanceRecord record = new FinanceRecord();
        record.setTitle(student.getName() + "(" + course.getName() + ")的退费申请");
        record.setAmount(amount.negate());
        record.setItemType(FinanceTypeEnum.REFUND);
        record.setItemId(item.getId());
        record.setOperator(staffId);
        record.setPayer(item.getStudentId());
        record.setRemark(item.getRemark());
        financeRecordService.addOne(record);

        // 学生课程的支付金额减少, 退费课次增加
        sc.setPaidAmount(sc.getPaidAmount().subtract(amount));
        sc.setCountLessonRefund(sc.getCountLessonRefund() + dto.getRefundLessonCount());
        studentCourseService.updateById(sc);

        return true;
    }

    @Override
    public Refund getOneByStudentCourseId(Long scId) {
        QueryWrapper<Refund> qw = new QueryWrapper<>();
        qw.eq("student_course_id", scId);
        return this.getOne(qw);
    }


}
