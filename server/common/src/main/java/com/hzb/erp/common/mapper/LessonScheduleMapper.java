package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.LessonSchedule;
import com.hzb.erp.common.pojo.bo.LessonDatetimeAndTeacherBO;
import com.hzb.erp.common.pojo.dto.LessonScheduleParamDTO;
import com.hzb.erp.common.pojo.vo.LessonScheduleVO;

import java.util.List;

/**
 * <p>
 * 课次编排 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
public interface LessonScheduleMapper extends BaseMapper<LessonSchedule> {
    @DataScoped
    IPage<LessonScheduleVO> getList(Page<Object> objectPage, LessonScheduleParamDTO param);
    @DataScoped
    List<Long> checkConflict(LessonDatetimeAndTeacherBO bo);
}
