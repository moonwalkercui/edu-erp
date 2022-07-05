package com.hzb.erp.api.pc.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.pc.course.entity.CourseTrial;
import com.hzb.erp.api.pc.course.entity.CourseTrialRecord;
import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.api.pc.student.entity.StudentCourse;
import com.hzb.erp.exception.BizException;
import com.hzb.erp.api.pc.course.mapper.CourseTrialMapper;
import com.hzb.erp.api.pc.course.mapper.CourseTrialRecordMapper;
import com.hzb.erp.api.pc.student.mapper.StudentMapper;
import com.hzb.erp.api.pc.course.pojo.CourseTrialRecordParamDTO;
import com.hzb.erp.api.pc.student.pojo.StudentCourseSaveDTO;
import com.hzb.erp.api.pc.course.pojo.CourseTrialRecordVO;
import com.hzb.erp.api.pc.course.service.CourseTrialRecordService;
import com.hzb.erp.api.pc.student.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 课程体验卡 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class CourseTrialRecordServiceImpl extends ServiceImpl<CourseTrialRecordMapper, CourseTrialRecord> implements CourseTrialRecordService {

    @Autowired
    private CourseTrialMapper courseTrialMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentCourseService studentCourseService;

    @Override
    public IPage<CourseTrialRecordVO> getList(CourseTrialRecordParamDTO param) {
        return baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public List<CourseTrialRecordVO> getAll(CourseTrialRecordParamDTO param) {
        return baseMapper.getList(param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean getOne(Long trialId, Student student) {

        CourseTrial courseTrial = courseTrialMapper.selectById(trialId);
        if(courseTrial == null) {
            throw new BizException("未知体验卡");
        }
        if(courseTrial.getQuantity() <=0) {
            throw new BizException("该体验卡已领完");
        }
        if(LocalDate.now().isAfter(courseTrial.getEndDate())) {
            throw new BizException("该体验卡已停止发行");
        }

        QueryWrapper<CourseTrialRecord> qw = new QueryWrapper<>();
        qw.eq("student_id", student.getId()).eq("trial_id", trialId).last("limit 1");
        if(this.baseMapper.selectOne(qw) != null) {
            throw new BizException("请勿重复领取");
        }

        courseTrial.setQuantity(courseTrial.getQuantity() - 1);
        courseTrialMapper.updateById(courseTrial);

        Long studentId = student.getId();
        Long userId = student.getUserId();
        LocalDate expiredDate = LocalDate.now().plusDays(courseTrial.getExpireDays());

        StudentCourseSaveDTO saveDTO = new StudentCourseSaveDTO();
        saveDTO.setStudentId(studentId);
        saveDTO.setStudentName(student.getName());
        saveDTO.setCourseId(courseTrial.getCourseId());
        saveDTO.setCourseAmount(BigDecimal.ZERO);
        saveDTO.setPaidAmount(BigDecimal.ZERO);
        saveDTO.setCountLessonTotal(courseTrial.getLessonCount());
        saveDTO.setDiscount(BigDecimal.ZERO);
        saveDTO.setExpireDate(expiredDate);
        saveDTO.setStartDate(LocalDate.now());
        saveDTO.setRemark("来自体验卡");
        // 创建学生课程中间数据，并记录财务数据
        StudentCourse studentCourse = studentCourseService.addOne(saveDTO, null);

        CourseTrialRecord record = new CourseTrialRecord();
        record.setStudentCourseId(studentCourse.getId());
        record.setTrialId(trialId);
        record.setStudentId(studentId);
        record.setLessonCount(courseTrial.getLessonCount());
        record.setUserId(userId);
        record.setExpiredDate(LocalDate.now().plusDays(courseTrial.getExpireDays()));
        record.setAddTime(LocalDateTime.now());
        this.baseMapper.insert(record);

        return true;
    }

}
