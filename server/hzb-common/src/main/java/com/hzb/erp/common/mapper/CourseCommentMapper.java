package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.CourseComment;
import com.hzb.erp.common.pojo.dto.CourseCommentParamDTO;
import com.hzb.erp.common.pojo.vo.CourseCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程大纲
 */
public interface CourseCommentMapper extends BaseMapper<CourseComment> {

    IPage<CourseCommentVO> getList(Page<Object> objectPage, CourseCommentParamDTO param);

    List<CourseCommentVO> getList(@Param("param") CourseCommentParamDTO param);

}
