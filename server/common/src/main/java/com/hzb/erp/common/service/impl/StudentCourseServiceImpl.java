package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.enums.LessonCountChangeStageEnum;
import com.hzb.erp.common.pojo.dto.PayOverdueDTO;
import com.hzb.erp.service.ImportExportService;
import com.hzb.erp.common.entity.Course;
import com.hzb.erp.common.entity.FinanceRecord;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.entity.StudentCourse;
import com.hzb.erp.common.enums.FinanceTypeEnum;
import com.hzb.erp.common.enums.StudentStageEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.StudentCourseMapper;
import com.hzb.erp.common.pojo.dto.StudentCourseParamDTO;
import com.hzb.erp.common.pojo.dto.StudentCourseSaveDTO;
import com.hzb.erp.common.pojo.vo.StudentCourseVO;
import com.hzb.erp.common.service.*;
import com.hzb.erp.utils.SettingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学员合约表 服务实现类
 * </p>
 *
 * @author 541720500@qq.com
 */
@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements StudentCourseService {

    @Autowired
    private CourseService courseService;

    @Autowired
    private FinanceRecordService financeRecordService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private StudentLessonCountLogService studentLessonCountLogService;

    @Autowired
    private ImportExportService importExportService;

    @Override
    public IPage<StudentCourseVO> getList(StudentCourseParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    static final String SELECT_REMAINING_FIELD = "count_lesson_total - count_lesson_complete - count_lesson_refund";

    @Override
    public List<StudentCourseVO> getAll(StudentCourseParamDTO param) {
        return this.baseMapper.getList(param);
    }

    @Override
    public void exportExcel(StudentCourseParamDTO param) {
        List<StudentCourseVO> list = getAll(param);
        LinkedHashMap<String, String> header = new LinkedHashMap<String, String>() {{
            put("id", "编码");
            put("studentName", "姓名");
            put("courseName", "课程");
            put("subjectName", "科目");
            put("countLessonTotal", "购买总课次");
            put("countLessonComplete", "已上课次");
            put("countLessonRemaining", "剩余课次");
            put("unitPrice", "课程单间");
            put("remainingAmount", "剩余金额");
            put("expireDate", "过期时间");
            put("addTime", "添加时间");
            put("amount", "合约金额");
            put("paidAmount", "实付金额");
            put("arrearage", "欠费");
            put("startDate", "开始日期");
            put("refundAmount", "退费总金额");
            put("refundLessonCount", "退费总次数");
            put("refundRemark", "退费说明");
        }};
        importExportService.exportExcel(header, list, "课时汇总记录");
    }

    @Override
    public Boolean editLessonExpireDate(Long id, LocalDate expireDate, Long currentUserId) {
        if (expireDate == null) {
            throw new BizException("未设置日期");
        }
        StudentCourse sc = this.getById(id);
        sc.setExpireDate(expireDate);
        return updateById(sc);
    }


    @Override
    public StudentCourseVO getInfo(Long id) {
        return this.baseMapper.getInfo(id);
    }

    @Override
    public void editLessonCountComplete(Long id, Integer countLessonComplete, String remark, Long staffId) {
        if (countLessonComplete < 0) {
            throw new BizException("已上课时不能小于零");
        }
        StudentCourse sc = this.getById(id);
        Integer oldCount = sc.getCountLessonComplete();

        if (countLessonComplete > sc.getCountLessonTotal()) {
            throw new BizException("已上课时不能大于总课时");
        }
        sc.setCountLessonComplete(countLessonComplete);
        this.updateById(sc);

        studentLessonCountLogService.handleAdd(sc.getStudentId(), sc.getCourseId(), oldCount - countLessonComplete, null, LessonCountChangeStageEnum.ADMIN, "调整原因:" + remark, staffId);

    }

    @Override
    public void decLessonCount(Integer id, Integer count) {
        StudentCourse sc = this.getById(id);
        count = Math.abs(count);
        if (count > getLessonRemainingCount(sc)) {
            throw new BizException("减少课时数不能大于余量");
        }
        sc.setCountLessonComplete(sc.getCountLessonComplete() + count);
        this.updateById(sc);
    }

    @Override
    public Integer getLessonRemainingCount(StudentCourse sc) {
        return sc.getCountLessonTotal() - sc.getCountLessonComplete() - sc.getCountLessonRefund();
    }

    @Override
    public Integer listLessonRemainingCountTotal(Long courseId, Long studentId) {
        return baseMapper.listLessonRemainingCountTotal(courseId, studentId);
    }

    @Override
    public StudentCourse getEnoughLessonCount(Long courseId, Long studentId, Integer decCount) {
        // 找到数量足够的那个签约记录
        QueryWrapper<StudentCourse> qw = new QueryWrapper<>();
        qw.eq("student_id", studentId)
                .eq("course_id", courseId)
                .ge("expire_date", LocalDate.now().toString())
                .ge(SELECT_REMAINING_FIELD, decCount)
                .orderByDesc("priority")
                .orderByAsc("id")
                .last("limit 1");
        return this.getOne(qw);
    }

    @Override
    public BigDecimal getTotalLessonRemainingCount(Long studentId) {
        QueryWrapper<StudentCourse> qw = new QueryWrapper<>();
        qw.eq("student_id", studentId)
                .ge("expire_date", LocalDate.now().toString())
                .select("sum(" + SELECT_REMAINING_FIELD + ") as total");
        Map<String, Object> map = this.getMap(qw);
        if (map == null) {
            return null;
        }
        return (BigDecimal) map.get("total");
    }

    @Override
    public List<StudentCourse> getWarningList() {
        Integer warningCount = settingService.intValue(SettingConstants.LESSON_COUNT_LESS_WARNING_COUNT);
        if (warningCount == null || warningCount <= 0) {
            return null;
        }
        Integer warningTimes = settingService.intValue(SettingConstants.LESSON_COUNT_LESS_WARNING_TIMES);
        if (warningTimes == null || warningTimes <= 0) {
            warningTimes = 0;
        }
        QueryWrapper<StudentCourse> qw = new QueryWrapper<>();
        qw.ge("expire_date", LocalDate.now().toString())
                .lt("warning_times", warningTimes)
                .le(SELECT_REMAINING_FIELD, warningCount);
        return list(qw);
    }

    /**
     * 消费学生的课次
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean decLessonCount(Long courseId, Long studentId, Integer decCount) {

        decCount = Math.abs(decCount);

        Student student = studentService.getById(studentId);
        StudentCourse selectOne = getEnoughLessonCount(courseId, studentId, decCount);
        if (selectOne == null) {
            throw new BizException(student.getName() + "的课次不够了,无法继续");
        }
        // 消耗课次
        selectOne.setCountLessonComplete(selectOne.getCountLessonComplete() + decCount);

        return this.updateById(selectOne);
    }

//    @Override
//    public Integer getStudentSubjectNum(Long studentId, Long subjectId) {
//        return this.baseMapper.getStudentSubjectNum(studentId, subjectId);
//    }

    @Override
    public Boolean delete(List<Long> ids) {
        // 作废合约 逻辑处理 todo
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOne(StudentCourseSaveDTO postData, Long operator) {

        BigDecimal discount = postData.getDiscount() == null ? new BigDecimal("0") : postData.getDiscount().abs();
        postData.setCourseAmount(postData.getCourseAmount().abs());

        StudentCourse sc = new StudentCourse();
        Course course = courseService.getById(postData.getCourseId());
        Student student = studentService.getById(postData.getStudentId());

        // 验证输入
        validatePostData(postData, course);

        sc.setId(postData.getId());

        if (postData.getOperator() != null) {
            operator = postData.getOperator();
        }
        sc.setOperator(operator);
        sc.setStudentId(postData.getStudentId());
        sc.setCourseId(postData.getCourseId());
        sc.setSubjectId(course.getSubjectId());
        sc.setStartDate(postData.getStartDate());
        sc.setExpireDate(postData.getExpireDate());
        sc.setRemark(postData.getRemark());
        sc.setCountLessonTotal(postData.getCountLessonTotal());
        sc.setCountLessonComplete(0);
        sc.setCourseAmount(postData.getCourseAmount());
        sc.setPaidAmount(postData.getPaidAmount());
        sc.setDiscountAmount(discount);
        sc.setAmount(postData.getCourseAmount());
        sc.setUnitPrice(course.getUnitPrice());

        sc.setPayOff(sc.getAmount().compareTo(sc.getPaidAmount()) < 1);
        this.save(sc);

        // 意向学员变在学学员
        if (StudentStageEnum.INTENTION.equals(student.getStage())) {
            studentService.changeStage(Collections.singletonList(student.getId()), StudentStageEnum.STUDYING, false);
        }

        // 财务记录
        FinanceRecord record = new FinanceRecord();
        record.setTitle(course.getName());
        record.setAmount(sc.getPaidAmount());
        record.setItemType(FinanceTypeEnum.COURSE);
        record.setItemId(sc.getId());
        record.setOperator(operator);
        record.setPayer(sc.getStudentId());
        record.setRemark(sc.getRemark());
        financeRecordService.addOne(record);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean payOverdue(PayOverdueDTO dto, Long operator) {
        StudentCourse sc = this.getById(dto.getId());
        sc.setPaidAmount(sc.getPaidAmount().add(dto.getAmount()));
        sc.setPayOff(sc.getAmount().compareTo(sc.getPaidAmount()) < 1);
        updateById(sc);

        Course course = courseService.getById(sc.getCourseId());

        if (dto.getOperator() != null) {
            operator = dto.getOperator();
        }
        // 财务记录
        FinanceRecord record = new FinanceRecord();
        record.setTitle(course.getName());
        record.setAmount(dto.getAmount());
        record.setItemType(FinanceTypeEnum.SUPPLEMENT);
        record.setItemId(sc.getId());
        record.setOperator(operator);
        record.setPayer(sc.getStudentId());
        financeRecordService.addOne(record);

        return true;
    }

    @Override
    public Boolean changePriority(Long id, Integer value) {
        StudentCourse sc = getById(id);
        sc.setPriority(value);
        return updateById(sc);
    }

    @Override
    public void editOne(StudentCourseSaveDTO postData) {
        BigDecimal discount = postData.getDiscount() == null ? new BigDecimal("0") : postData.getDiscount().abs();
        postData.setCourseAmount(postData.getCourseAmount().abs());

        StudentCourse sc = this.getById(postData.getId());
        Course course = courseService.getById(postData.getCourseId());

        // 验证输入
        validatePostData(postData, course);

        if (postData.getStartDate() != null) {
            sc.setStartDate(postData.getStartDate());
        }

        if (postData.getExpireDate() != null) {
            sc.setExpireDate(postData.getExpireDate());
        }

        if (postData.getCountLessonTotal() != null) {
            sc.setCountLessonTotal(postData.getCountLessonTotal());
        }

        if (postData.getCourseAmount() != null) {
            sc.setCourseAmount(postData.getCourseAmount());
        }

//        if (postData.getCountLessonRemaining() != null) {
//            sc.setCountLessonRemaining(postData.getCountLessonRemaining());
//        }

        sc.setDiscountAmount(discount);

        if (postData.getRemark() != null) {
            sc.setRemark(postData.getRemark());
        }

        this.updateById(sc);
    }


    /**
     * 验证提交数据的准确性
     */
    private void validatePostData(StudentCourseSaveDTO postData, Course course) {

        if (postData.getId() != null && !course.getId().equals(postData.getCourseId())) {
            throw new BizException("报单后的合约ID无法修改");

//        } else if (postData.getCourseAmount() != null
//                && !course.getPrice().equals(postData.getCourseAmount())) {
//            throw new BizException("课程金额有误！提示：要变更成交金额，请在折扣里调整");

        } else if (
                (postData.getStartDate() != null && postData.getExpireDate() != null)
                        &&
                        (postData.getStartDate().isAfter(postData.getExpireDate()) || postData.getStartDate().isEqual(postData.getExpireDate()))
        ) {
            throw new BizException("开始时间只能在有效期截止日期之前");

        } else if (postData.getDiscount() != null && postData.getDiscount().compareTo(postData.getCourseAmount()) > 0) {
            throw new BizException("优惠金额不能大于成交金额");
        }
    }
}
