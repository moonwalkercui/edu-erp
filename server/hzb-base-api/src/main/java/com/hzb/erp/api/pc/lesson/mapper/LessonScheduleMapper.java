package com.hzb.erp.api.pc.lesson.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.api.pc.lesson.entity.LessonSchedule;
import com.hzb.erp.api.pc.lesson.pojo.LessonDatetimeAndTeacherBO;
import com.hzb.erp.api.pc.lesson.pojo.LessonScheduleParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.LessonScheduleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 课次编排 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface LessonScheduleMapper extends BaseMapper<LessonSchedule> {
    @DataScoped
    IPage<LessonScheduleVO> getList(Page<Object> objectPage, LessonScheduleParamDTO param);

    @DataScoped
    List<Long> checkConflict(LessonDatetimeAndTeacherBO bo);
}
