package com.hzb.erp.api.pc.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.annotation.DataScoped;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 统计相关mapper
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface StatsMapper extends BaseMapper<Object> {

    @MapKey("dates")
    @DataScoped
    List<Map<String, Object>> newStudentCounts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @MapKey("dates")
    @DataScoped
    List<Map<String, Object>> courseSalesByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @MapKey("course_id")
    @DataScoped
    List<Map<String, Object>> courseSalesTotal(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @MapKey("teacher_id")
    @DataScoped
    List<Map<String, Object>> teacherLessonTotal(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @MapKey("studentLessonCounts")
    @DataScoped
    List<Map<String, Object>> studentLessonCounts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    Map<String, Object> dashboardCounts();

    @MapKey("stage")
    List<Map<String, Object>> funnelData();

    @MapKey("name")
    @DataScoped
    List<Map<String, Object>> studentAgeStats();

    @MapKey("teacher_name")
    @DataScoped
    List<Map<String, Object>> teachEvaluationStatis(@Param("teacherId") Long teacherId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
