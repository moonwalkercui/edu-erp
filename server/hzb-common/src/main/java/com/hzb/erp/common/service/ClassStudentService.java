package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.ClassStudent;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.pojo.dto.ClassStudentAddDTO;

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

    Boolean deleteClassStudent(Long classId, Long studentId);

    Boolean deleteClassStudentByIds(List<Long> ids);

    Boolean addClassStudent(Long classId, Long studentId, Long staffId);

    void addClassStudents(ClassStudentAddDTO dto, Long staffId);

    List<Student> getStudentsByClassId(Long classId);

    boolean changeConsumeCourse(Long classStudentId, Long consumeCourseId);

}
