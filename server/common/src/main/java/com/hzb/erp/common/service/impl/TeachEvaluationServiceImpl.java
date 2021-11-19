package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.TeachEvaluation;
import com.hzb.erp.common.pojo.dto.LessonParamDTO;
import com.hzb.erp.common.pojo.vo.TeachEvaluationVO;
import com.hzb.erp.common.service.TeachEvaluationService;
import com.hzb.erp.common.mapper.TeachEvaluationMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class TeachEvaluationServiceImpl extends ServiceImpl<TeachEvaluationMapper, TeachEvaluation> implements TeachEvaluationService {

    @Override
    public IPage<TeachEvaluationVO> getList(LessonParamDTO param) {
        return baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }
}




