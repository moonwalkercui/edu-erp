package com.hzb.erp.api.pc.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.api.pc.course.entity.CourseSection;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 课程大纲
 */
@Mapper
@Repository
public interface CourseSectionMapper extends BaseMapper<CourseSection> {
}




