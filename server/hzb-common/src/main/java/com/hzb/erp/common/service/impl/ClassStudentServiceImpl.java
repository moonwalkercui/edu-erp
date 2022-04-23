package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.ClassStudent;
import com.hzb.erp.common.entity.Clazz;
import com.hzb.erp.common.entity.LessonStudent;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.enums.SignStateEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.ClassStudentMapper;
import com.hzb.erp.common.mapper.ClazzMapper;
import com.hzb.erp.common.mapper.LessonStudentMapper;
import com.hzb.erp.common.pojo.dto.ClassStudentAddDTO;
import com.hzb.erp.common.service.ClassStudentService;
import com.hzb.erp.common.service.ClazzService;
import com.hzb.erp.common.service.LessonStudentService;
import com.hzb.erp.common.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 班级学员表 服务实现类
 * </p>
 *
 * @author 541720500@qq.com
 */
@Service
public class ClassStudentServiceImpl extends ServiceImpl<ClassStudentMapper, ClassStudent> implements ClassStudentService {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private LessonStudentService lessonStudentService;
    @Autowired
    private LessonStudentMapper lessonStudentMapper;

    /**
     * 添加学员
     */
    @Override
    public Boolean addClassStudent(Long classId, Long studentId, Long staffId) {
        Clazz clazz = clazzMapper.selectById(classId);
        QueryWrapper<ClassStudent> queryWrapper = buildQW(classId, studentId);
        ClassStudent item = this.getOne(queryWrapper);
        Boolean res;
        if (item != null) {
            if (!item.getDeleted()) {
                throw new BizException("请勿重复添加");
            }
            item.setDeleted(false);
            item.setAddTime(LocalDateTime.now());
            item.setCreator(staffId);
            item.setConsumeCourseId(clazz.getCourseId());
            res = this.updateById(item);
        } else {
            res = this.save(buildOne(clazz, studentId));
        }

        if (res) {
            generateLessonStudent(clazz, studentId);
        }

        return res;
    }

    private Boolean generateLessonStudent(Clazz clazz, Long studentId) {
        List<Long> generateLessonIds = lessonStudentMapper.listGenerateLessonIdsByClassId(clazz.getId());
        List<LessonStudent> exist = lessonStudentService.listByStudentId(studentId);
        List<Long> existLessonIds = exist.stream().map(LessonStudent::getLessonId).collect(Collectors.toList());
        Student student = studentService.getById(studentId);
        List<LessonStudent> lsList = new ArrayList<>();
        for (Long lessonId : generateLessonIds) {
            if (existLessonIds.contains(lessonId)) {
                continue;
            }
            LessonStudent newLs = new LessonStudent();
            newLs.setLessonId(lessonId);
            newLs.setClassId(clazz.getId());
            newLs.setStudentId(studentId);
            newLs.setSignState(SignStateEnum.NONE);
            newLs.setConsumeCourseId(clazz.getCourseId());
            newLs.setCounselor(student.getCounselor());
            lsList.add(newLs);
        }
        boolean res = false;
        if (lsList.size() > 0) {
            res = lessonStudentService.saveBatch(lsList);
        }
        return res;
    }

    @Override
    public void addClassStudents(ClassStudentAddDTO dto, Long staffId) {
        List<ClassStudent> list = new ArrayList<>();
        Clazz clazz = clazzMapper.selectById(dto.getClassId());
        for (Long studentId : dto.getStudentIds()) {
            QueryWrapper<ClassStudent> queryWrapper = buildQW(dto.getClassId(), studentId);
            ClassStudent item = this.getOne(queryWrapper);
            if (item == null) {
                list.add(buildOne(clazz, studentId));
            } else {
                item.setDeleted(false);
                item.setAddTime(LocalDateTime.now());
                item.setCreator(staffId);
                item.setConsumeCourseId(clazz.getCourseId());
                this.updateById(item);
            }
            generateLessonStudent(clazz, studentId);
        }
        if (list.size() > 0) {
            this.saveBatch(list);
        }
    }

    @Override
    public List<Student> getStudentsByClassId(Long classId) {

        QueryWrapper<ClassStudent> qw = new QueryWrapper<>();
        qw.eq("class_id", classId);
        List<ClassStudent> csList = list(qw);
        if (csList == null || csList.size() == 0) {
            return null;
        }

        QueryWrapper<Student> studentQw = new QueryWrapper<>();
        studentQw.in("id", csList.stream().map(ClassStudent::getStudentId).collect(Collectors.toList()));
        return studentService.list(studentQw);

    }

    @Override
    public boolean changeConsumeCourse(Long classStudentId, Long consumeCourseId) {
        ClassStudent item = getById(classStudentId);
        item.setConsumeCourseId(consumeCourseId);
        return updateById(item);
    }

    private ClassStudent buildOne(Clazz clazz, Long studentId) {
        ClassStudent cs = new ClassStudent();
        cs.setClassId(clazz.getId());
        cs.setStudentId(studentId);
        cs.setConsumeCourseId(clazz.getCourseId());
        return cs;
    }

//    @Override
//    public IPage<ClassStudentVO> getList(ClassStudentParamDTO param) {
//        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
//    }
//
//    @Override
//    public List<ClassStudentVO> getAll(ClassStudentParamDTO param) {
//        return this.baseMapper.getList(param);
//    }

    /**
     * 移除学员
     */
    @Override
    public Boolean deleteClassStudent(Long classId, Long studentId) {
        QueryWrapper<ClassStudent> queryWrapper = buildQW(classId, studentId);
        ClassStudent cs = this.getOne(queryWrapper);
        cs.setDeleted(true);
        Boolean res = this.updateById(cs);
        if (res) {
            lessonStudentMapper.removeByStudentIdAndClassId(cs.getStudentId(), cs.getClassId());
        }
        return res;
    }

    @Override
    public int deleteClassStudent(List<Long> studentIds) {
        QueryWrapper<ClassStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("student_id", studentIds);
        return this.baseMapper.delete(queryWrapper);
    }

    /**
     * 批量移除学员
     */
    @Override
    public Boolean deleteClassStudentByIds(List<Long> ids) {
        List<ClassStudent> list = this.listByIds(ids);
        for (ClassStudent cs : list) {
            cs.setDeleted(true);
        }
        Boolean res = this.updateBatchById(list);
        if (res) {
            for (ClassStudent cs : list) {
                lessonStudentMapper.removeByStudentIdAndClassId(cs.getStudentId(), cs.getClassId());
            }
        }
        return res;
    }

    private QueryWrapper<ClassStudent> buildQW(Long classId, Long studentId) {
        QueryWrapper<ClassStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id", classId).eq("student_id", studentId);
        return queryWrapper;
    }
}
