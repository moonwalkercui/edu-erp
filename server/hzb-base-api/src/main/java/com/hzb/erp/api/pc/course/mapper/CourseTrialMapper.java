package com.hzb.erp.api.pc.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.course.entity.CourseTrial;
import com.hzb.erp.api.pc.course.pojo.CourseTrialParamDTO;
import com.hzb.erp.api.pc.course.pojo.CourseTrialVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 体验卡
 */
@Mapper
public interface CourseTrialMapper extends BaseMapper<CourseTrial> {
    IPage<CourseTrialVO> getList(Page<?> objectPage, CourseTrialParamDTO param);
    List<CourseTrialVO> getList(@Param("param") CourseTrialParamDTO param);
}
