package com.hzb.erp.api.pc.lesson.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.lesson.entity.LessonTeacher;

import java.util.List;

/**
 * <p>
 * 课时老师关联表 服务类
 * </p>
 *
 * @author Ryan
 */
public interface LessonTeacherService extends IService<LessonTeacher> {
    void validateTeacherIds(List<Long> teacherIds, List<Long> assistantIds);

    Boolean deleteByLessonId(Long id);
}
