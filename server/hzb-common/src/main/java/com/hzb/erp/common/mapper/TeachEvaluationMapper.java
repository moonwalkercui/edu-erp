package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.TeachEvaluation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.pojo.dto.LessonParamDTO;
import com.hzb.erp.common.pojo.vo.TeachEvaluationVO;

/**
 * com.hzb.erp.common.com.hzb.erp.common.TeachEvaluation
 */
public interface TeachEvaluationMapper extends BaseMapper<TeachEvaluation> {

    @DataScoped
    IPage<TeachEvaluationVO> getList(Page<Object> objectPage, LessonParamDTO param);
}




