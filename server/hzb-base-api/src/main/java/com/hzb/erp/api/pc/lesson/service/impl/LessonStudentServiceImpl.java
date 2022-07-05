package com.hzb.erp.api.pc.lesson.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.pc.lesson.entity.Lesson;
import com.hzb.erp.api.pc.lesson.entity.LessonStudent;
import com.hzb.erp.api.pc.lesson.service.LessonStudentService;
import com.hzb.erp.api.pc.student.entity.StudentCourse;
import com.hzb.erp.api.pc.student.entity.StudentCreditLog;
import com.hzb.erp.api.pc.student.service.StudentCourseService;
import com.hzb.erp.api.pc.student.service.StudentLessonCountLogService;
import com.hzb.erp.api.pc.student.service.StudentService;
import com.hzb.erp.common.enums.LessonCountChangeStageEnum;
import com.hzb.erp.common.enums.SignStateEnum;
import com.hzb.erp.common.enums.SignTypeEnum;
import com.hzb.erp.common.enums.StudentCreditChangeTypeEnum;
import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.exception.BizException;
import com.hzb.erp.api.pc.lesson.mapper.LessonMapper;
import com.hzb.erp.api.pc.lesson.mapper.LessonStudentMapper;
import com.hzb.erp.api.pc.lesson.pojo.LessonEvaluateSaveDTO;
import com.hzb.erp.api.pc.lesson.pojo.LessonStudentParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.LessonStudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课时学员关联表 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class LessonStudentServiceImpl extends ServiceImpl<LessonStudentMapper, LessonStudent> implements LessonStudentService {

    @Autowired
    private StudentCourseService studentCourseService;
    @Autowired
    private LessonStudentMapper lessonStudentMapper;
    @Autowired
    private StudentLessonCountLogService studentLessonCountLogService;
    @Autowired
    private StudentService studentService;
    @Resource
    private LessonMapper lessonMapper;

    @Override
    public IPage<LessonStudentVO> getList(LessonStudentParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public List<LessonStudentVO> getAll(LessonStudentParamDTO param) {
        return this.baseMapper.getList(param);
    }

    @Override
    public LessonStudent getByLessonIdAndStudentId(Long lessonId, Long studentId) {
        QueryWrapper<LessonStudent> qw = new QueryWrapper<>();
        qw.eq("lesson_id", lessonId).eq("student_id", studentId).last("limit 1");
        return this.getOne(qw);
    }

    @Override
    public List<LessonStudent> listByStudentId(Long studentId) {
        QueryWrapper<LessonStudent> qw = new QueryWrapper<>();
        qw.eq("student_id", studentId);
        return list(qw);
    }

    @Override
    public Boolean calDecLessonCountByState(LessonStudent ls, SignStateEnum state) {
        return (ls != null && (ls.getDecLessonCount() == null || ls.getDecLessonCount() == 0)) && (SignStateEnum.NORMAL.equals(state) || SignStateEnum.LATE.equals(state));
    }

    @Override
    public boolean evaluation(LessonEvaluateSaveDTO dto, Long teacherId) {
        LessonStudent ls = getById(dto.getId());
        ls.setScore(dto.getScore());
        ls.setEvaluation(dto.getEvaluation());
        ls.setEvaluateTime(LocalDateTime.now());
        ls.setEvaluateTeacher(teacherId);
        boolean res = updateById(ls);

        StudentCreditLog creditLog = new StudentCreditLog();
        creditLog.setStudentId(ls.getStudentId());
        creditLog.setCredit(dto.getScore());
        creditLog.setChangeType(StudentCreditChangeTypeEnum.LESSON_EVALUATE);
        creditLog.setSourceId(ls.getId());
        studentService.incCredit(creditLog);

        return res;

    }

    @Override
    public boolean rollbackCourseNum(List<Long> ids, Long teacherId) {

        List<LessonStudent> list = new ArrayList<>();
        for (Long id : ids) {
            if (id == null) {
                throw new BizException("无法返还无状态的记录");
            }
            LessonStudent ls = getById(id);
            if (ls.getDecLessonCount() == null || ls.getDecLessonCount() == 0) {
                throw new BizException("无法返还未消课的的记录");
            }
            if (ls.getConsumeCourseId() == null) {
                throw new BizException("无法返还未设置消费课程的的记录");
            }
            list.add(ls);
        }

        for (LessonStudent item : list) {
            int count = item.getDecLessonCount();
            item.setDecLessonCount(0);
            updateById(item);
            rollcallCancel(item.getStudentId(), item.getConsumeCourseId(), count, teacherId);
        }
        return list.size() > 0;
    }

    /**
     * 生成或更新点名记录、消课逻辑
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LessonStudent addOne(Long lessonId, Long studentId, Long classId, Integer decLessonCount, SignStateEnum state, SignTypeEnum type, Long teacherId) {
        if (decLessonCount == 0) {
            return null;
        }
        decLessonCount = Math.abs(decLessonCount);
        LessonStudent ls = getByLessonIdAndStudentId(lessonId, studentId);
        // 找到消费课时
        Long consumeCourseId = lessonStudentMapper.getConsumeCourseId(lessonId, studentId);
        if (consumeCourseId == null) {
            throw new BizException("学员未设置消费课程，无法消课。");
        }
        if (ls != null) {

            // 更新记录时(修改出勤状态时) 检查之前是否扣过课时,如果扣过,那么就不扣了,如果没有扣过,则检查扣多少
            if (calDecLessonCountByState(ls, state)) {
                studentCourseService.decLessonCount(consumeCourseId, studentId, decLessonCount);
                ls.setDecLessonCount(decLessonCount);
                ls.setConsumeCourseId(consumeCourseId);
            }

            ls.setSignTime(LocalDateTime.now());
            ls.setSignType(type);
            ls.setSignState(state);
            ls.setTeacherId(teacherId);
            this.updateById(ls);

            return ls;

        } else {
            Student student = studentService.getById(studentId);
            LessonStudent newLs = new LessonStudent();
            newLs.setLessonId(lessonId);
            newLs.setClassId(classId);
            newLs.setStudentId(studentId);
            newLs.setSignTime(LocalDateTime.now());
            newLs.setSignType(type);
            newLs.setSignState(state);
            newLs.setTeacherId(teacherId);
            newLs.setConsumeCourseId(consumeCourseId);
            newLs.setCounselor(student.getCounselor());

            // 新增签到记录时 或者 强制扣课时时, 会扣减课时
            if (SignStateEnum.NORMAL.equals(state) || SignStateEnum.LATE.equals(state)) {
                studentCourseService.decLessonCount(consumeCourseId, studentId, decLessonCount);
                newLs.setDecLessonCount(decLessonCount);
            } else {
                newLs.setDecLessonCount(0);
            }

            this.save(newLs);
            return newLs;
        }

    }

    @Override
    public LessonStudent addOne(Long lessonId, Student student, SignStateEnum state) {
        Long studentId = student.getId();
        LessonStudent ls = getByLessonIdAndStudentId(lessonId, studentId);
        // 找到消费课时
        if (ls != null) {
            return null;
        }

        Lesson lesson = lessonMapper.selectById(lessonId);
        LessonStudent newLs = new LessonStudent();
        newLs.setLessonId(lessonId);
        newLs.setStudentId(studentId);
        newLs.setSignState(state);
        // 消课id
        newLs.setConsumeCourseId(lesson.getCourseId());
        newLs.setCounselor(student.getCounselor());
        this.save(newLs);
        return newLs;
    }

    /**
     * 取消消课
     */
    private void rollcallCancel(Long studentId, Long consumeCourseId, Integer decLessonCount, Long teacherId) {
        QueryWrapper<StudentCourse> qw = new QueryWrapper<>();
        qw.eq("student_id", studentId).eq("course_id", consumeCourseId).last("limit 1");
        StudentCourse record = studentCourseService.getOne(qw);
        if (record != null && decLessonCount != null && decLessonCount > 0) {
            // 减少完成数
            record.setCountLessonComplete(record.getCountLessonComplete() - decLessonCount);
            studentCourseService.updateById(record);
            // 记录
            studentLessonCountLogService.handleAdd(studentId, consumeCourseId, decLessonCount, null, LessonCountChangeStageEnum.ROLLBACK, "签到状态变更", teacherId);
        }
    }

}
