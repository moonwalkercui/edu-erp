package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.LessonStudent;
import com.hzb.erp.common.entity.StudentCourse;
import com.hzb.erp.common.entity.StudentLessonCountLog;
import com.hzb.erp.common.enums.LessonCountChangeStageEnum;
import com.hzb.erp.common.mapper.StudentCourseMapper;
import com.hzb.erp.common.mapper.StudentLessonCountLogMapper;
import com.hzb.erp.common.pojo.dto.StudentLessonLogParamDTO;
import com.hzb.erp.common.pojo.vo.StudentLessonCountLogVO;
import com.hzb.erp.common.service.LessonService;
import com.hzb.erp.common.service.StudentCourseService;
import com.hzb.erp.common.service.StudentLessonCountLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 学员课次记录 服务实现类
 * </p>
 *
 * @author 541720500@qq.com
 */
@Service
public class StudentLessonCountLogServiceImpl extends ServiceImpl<StudentLessonCountLogMapper, StudentLessonCountLog> implements StudentLessonCountLogService {

    @Autowired
    @Lazy
    private StudentCourseService studentCourseService;

    @Override
    public IPage<StudentLessonCountLogVO> getList(StudentLessonLogParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public Boolean addOneByLesson(LessonStudent ls, Long teacherId) {
        // 不能在一节课上重复消课
        QueryWrapper<StudentLessonCountLog> qw = new QueryWrapper<>();
        qw.eq("student_id", ls.getStudentId()).eq("lesson_id", ls.getLessonId()).last("limit 1");
        StudentLessonCountLog record = this.getOne(qw);
        if (record != null) {
            return false;
        }
        return handleAdd(ls.getStudentId(), ls.getConsumeCourseId(), -Math.abs(ls.getDecLessonCount()), ls.getLessonId(), LessonCountChangeStageEnum.LESSON_DEC, "消课", teacherId);
    }

    @Override
    public Boolean addOneByContract(Long studentId, Long courseId, Integer count, Long staffId) {
        return handleAdd(studentId, courseId, count, null, LessonCountChangeStageEnum.CONTRACT, "报名", staffId);
    }

    @Override
    public Boolean addOneByRefund(Long studentId, Long studentCourseId, Integer count, Long staffId) {
        if (count == 0) {
            return null;
        }
        StudentCourse sc = studentCourseService.getById(studentCourseId);
        return handleAdd(studentId, sc.getCourseId(), -count, null, LessonCountChangeStageEnum.REFUND, "退课", staffId);
    }

    @Override
    public Boolean addOneByRefundReject(Long studentId, Long studentCourseId, Integer count, Long staffId) {
        if (count==null || count == 0) {
            return null;
        }
        StudentCourse sc = studentCourseService.getById(studentCourseId);
        return handleAdd(studentId, sc.getCourseId(), count, null, LessonCountChangeStageEnum.REFUND_REJECT, "退课审核驳回", staffId);
    }

    @Override
    public Boolean handleAdd(Long studentId, Long courseId, Integer count, Long lessonId, LessonCountChangeStageEnum stage, String remark, Long staffId) {
        if (count == 0) {
            return false;
        }
        StudentLessonCountLog lcLog = new StudentLessonCountLog();
        lcLog.setCourseId(courseId);
        lcLog.setRemainingCount(studentCourseService.listLessonRemainingCountTotal(courseId, studentId));
        lcLog.setStudentId(studentId);
        lcLog.setChangeCount(count);
        lcLog.setStaffId(staffId);
        lcLog.setLessonId(lessonId);
        lcLog.setAddTime(LocalDateTime.now());
        lcLog.setStage(stage);
        lcLog.setRemark(remark);
        return this.save(lcLog);
    }

}
