package com.hzb.erp.api.pc.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.api.pc.course.entity.CourseImage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 课程介绍图片
 */
@Mapper
@Repository
public interface CourseImageMapper extends BaseMapper<CourseImage> {
}
