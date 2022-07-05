package com.hzb.erp.api.pc.lesson.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.lesson.entity.LessonStudent;
import com.hzb.erp.api.pc.lesson.pojo.LessonStudentParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.LessonStudentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课时学员关联表 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Repository
@Mapper
public interface LessonStudentMapper extends BaseMapper<LessonStudent> {

    IPage<LessonStudentVO> getList(Page<Object> objectPage, LessonStudentParamDTO param);

    List<LessonStudentVO> getList(@Param("param") LessonStudentParamDTO param);

    /**
     * 查找要消费的课程id
     */
    Long getConsumeCourseId(@Param("lessonId") Long lessonId, @Param("studentId") Long studentId);

    List<Long> getStudentIds(Long id);

    /**
     * 删除学生记录，包含未签到的
     */
    Boolean removeByStudentIdAndClassId(@Param("studentId") Long studentId, @Param("classId") Long classId);

    /**
     * 班级加入学员时，查询到要生成学生课次记录的课次id列表
     */
    List<Long> listGenerateLessonIdsByClassId(@Param("classId") Long classId);
}
