package com.hzb.erp.api.pc.clazz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.clazz.entity.ClassStudent;
import com.hzb.erp.api.pc.clazz.pojo.ClassStudentAddDTO;
import com.hzb.erp.api.pc.student.entity.Student;

import java.util.List;

/**
 * <p>
 * 班级学员表 服务类
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface ClassStudentService extends IService<ClassStudent> {

//    IPage<ClassStudentVO> getList(ClassStudentParamDTO param);
//
//    List<ClassStudentVO> getAll(ClassStudentParamDTO param);

    /**
    * 删除学员
    * */
    Boolean deleteClassStudent(Long classId, Long studentId);

    /**
     * 删除学员 根据学生id列表删除
     * */
    int deleteClassStudent(List<Long> studentIds);

    Boolean deleteClassStudentByIds(List<Long> ids);

    Boolean addClassStudent(Long classId, Long studentId, Long staffId);

    void addClassStudents(ClassStudentAddDTO dto, Long staffId);

    List<Student> getStudentsByClassId(Long classId);

    boolean changeConsumeCourse(Long classStudentId, Long consumeCourseId);

}
