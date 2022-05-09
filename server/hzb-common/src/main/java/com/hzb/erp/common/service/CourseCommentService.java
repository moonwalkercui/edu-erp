package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.CourseComment;
import com.hzb.erp.common.pojo.dto.CourseCommentParamDTO;
import com.hzb.erp.common.pojo.vo.CourseCommentVO;

/**
 * <p>
 * 课程评价 服务类
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface CourseCommentService extends IService<CourseComment> {
    IPage<CourseCommentVO> getList(CourseCommentParamDTO param);
}
