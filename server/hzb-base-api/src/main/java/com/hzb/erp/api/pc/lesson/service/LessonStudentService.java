package com.hzb.erp.api.pc.lesson.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.lesson.entity.LessonStudent;
import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.common.enums.SignStateEnum;
import com.hzb.erp.common.enums.SignTypeEnum;
import com.hzb.erp.api.pc.lesson.pojo.LessonEvaluateSaveDTO;
import com.hzb.erp.api.pc.lesson.pojo.LessonStudentParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.LessonStudentVO;

import java.util.List;

/**
 * <p>
 * 课时学员关联表 服务类
 * </p>
 *
 * @author Ryan
 */
public interface LessonStudentService extends IService<LessonStudent> {

    /**
     * 带分页的列表
     *
     * @param param 参数
     */
    IPage<LessonStudentVO> getList(LessonStudentParamDTO param);

    List<LessonStudentVO> getAll(LessonStudentParamDTO param);

    LessonStudent getByLessonIdAndStudentId(Long lessonId, Long studentId);

    List<LessonStudent> listByStudentId(Long studentId);

    /**
     * 点名和签到时用于增加签到记录,并扣减响应的课时.
     * @param lessonId 课时id
     * @param studentId 学生id
     * @param classId 班级id
     * @param decLessonCount 扣减数量
     * @param state 签到状态
     * @param teacherId 操作老师id
     */
    LessonStudent addOne(Long lessonId, Long studentId, Long classId, Integer decLessonCount, SignStateEnum state, SignTypeEnum type, Long teacherId);

    /**
     * 只添加课时和学生关系记录
     * @param lessonId 课时id
     * @param student 学生
     * @param state 签到状态
     */
    LessonStudent addOne(Long lessonId, Student student, SignStateEnum state);

    /**
     * 判断是否需要扣课时
     */
    Boolean calDecLessonCountByState(LessonStudent ls, SignStateEnum state);

    boolean evaluation(LessonEvaluateSaveDTO dto, Long teacherId);

    boolean rollbackCourseNum(List<Long> ids, Long teacherId);
}
