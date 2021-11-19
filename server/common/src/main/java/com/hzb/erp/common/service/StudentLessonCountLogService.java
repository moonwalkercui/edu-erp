package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.LessonStudent;
import com.hzb.erp.common.entity.StudentLessonCountLog;
import com.hzb.erp.common.enums.LessonCountChangeStageEnum;
import com.hzb.erp.common.pojo.dto.StudentLessonLogParamDTO;
import com.hzb.erp.common.pojo.vo.StudentLessonCountLogVO;

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

    Boolean handleAdd(Long studentId, Long courseId, Integer count, LessonCountChangeStageEnum stage, String remark, Long staffId);
}
