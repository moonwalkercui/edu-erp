package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.CourseTrial;
import com.hzb.erp.common.entity.CourseTrialRecord;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.entity.StudentCourse;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.CourseTrialMapper;
import com.hzb.erp.common.mapper.CourseTrialRecordMapper;
import com.hzb.erp.common.mapper.StudentMapper;
import com.hzb.erp.common.pojo.dto.CourseTrialRecordParamDTO;
import com.hzb.erp.common.pojo.dto.StudentCourseSaveDTO;
import com.hzb.erp.common.pojo.vo.CourseTrialRecordVO;
import com.hzb.erp.common.service.CourseTrialRecordService;
import com.hzb.erp.common.service.StudentCourseService;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Transactional
    public boolean getOne(Long trialId, Student student) {

        CourseTrial courseTrial = courseTrialMapper.selectById(trialId);
        if(courseTrial == null) {
            throw new BizException("未知体验卡");
        }
        if(courseTrial.getRemainingQuantity() <=0) {
            throw new BizException("该体验卡已领完");
        }
        if(LocalDate.now().isAfter(courseTrial.getEndDate())) {
            throw new BizException("该体验卡已停止发行");
        }

        courseTrial.setRemainingQuantity(courseTrial.getRemainingQuantity() - 1);
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
        record.setUserId(userId);
        record.setExpiredDate(LocalDate.now().plusDays(courseTrial.getExpireDays()));
        record.setAddTime(LocalDateTime.now());

        return true;
    }

}
