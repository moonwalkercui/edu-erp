package com.hzb.erp.api.pc.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.course.entity.CourseComment;
import com.hzb.erp.api.pc.course.pojo.CourseCommentParamDTO;
import com.hzb.erp.api.pc.course.pojo.CourseCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程大纲
 */
@Repository
@Mapper
public interface CourseCommentMapper extends BaseMapper<CourseComment> {

    IPage<CourseCommentVO> getList(Page<Object> objectPage, CourseCommentParamDTO param);

    List<CourseCommentVO> getList(@Param("param") CourseCommentParamDTO param);

    Double getFavRate(Long coureId);
}
