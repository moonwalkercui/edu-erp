package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.CourseComment;
import com.hzb.erp.common.mapper.CourseCommentMapper;
import com.hzb.erp.common.pojo.dto.CourseCommentParamDTO;
import com.hzb.erp.common.pojo.vo.CourseCommentVO;
import com.hzb.erp.common.service.CourseCommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程评论 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class ContactCommentServiceImpl extends ServiceImpl<CourseCommentMapper, CourseComment> implements CourseCommentService {

    @Override
    public IPage<CourseCommentVO> getList(CourseCommentParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }
}
