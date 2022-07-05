package com.hzb.erp.api.pc.lesson.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.api.pc.lesson.entity.TeachEvaluation;
import com.hzb.erp.api.pc.lesson.pojo.LessonParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.TeachEvaluationVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * com.hzb.erp.common.com.hzb.erp.common.TeachEvaluation
 */
@Mapper
public interface TeachEvaluationMapper extends BaseMapper<TeachEvaluation> {

    @DataScoped
    IPage<TeachEvaluationVO> getList(Page<Object> objectPage, LessonParamDTO param);
}




