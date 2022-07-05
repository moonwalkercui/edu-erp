package com.hzb.erp.api.pc.student.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.lesson.entity.LessonStudent;
import com.hzb.erp.api.pc.student.entity.StudentLessonCountLog;
import com.hzb.erp.common.enums.LessonCountChangeStageEnum;
import com.hzb.erp.api.pc.student.pojo.StudentLessonLogParamDTO;
import com.hzb.erp.api.pc.student.pojo.StudentLessonCountLogVO;

/**
 * <p>
 * 学员课次记录 服务类
 * </p>
 *
 * @author Ryan
 */
public interface StudentLessonCountLogService extends IService<StudentLessonCountLog> {
    IPage<StudentLessonCountLogVO> getList(StudentLessonLogParamDTO param);

    Boolean addOneByLesson(LessonStudent lessonStudent, Long teacherId);

    Boolean addOneByRefund(Long studentId, Long studentCourseId, Integer count, Long staffId);

    Boolean addOneByRefundReject(Long studentId, Long studentCourseId, Integer count, Long staffId);

    Boolean addOneByContract(Long studentId, Long courseId, Integer count, Long staffId);

    /***
     * 添加上课记录
     * @param studentId 学员id
     * @param courseId 课程id
     * @param count 变动数量
     * @param lessonId 相关课时id
     * @param stage 阶段
     * @param remark 备注
     * @param staffId 操作老师id
     * @return java.lang.Boolean
     *
     * */
    Boolean handleAdd(Long studentId, Long courseId, Integer count, Long lessonId, LessonCountChangeStageEnum stage, String remark, Long staffId);
}
