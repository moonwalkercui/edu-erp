package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hzb.erp.common.entity.TeachEvaluation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.pojo.dto.LessonParamDTO;
import com.hzb.erp.common.pojo.vo.TeachEvaluationVO;

/**
 *
 */
public interface TeachEvaluationService extends IService<TeachEvaluation> {

    /**
     * 带分页的列表
     *
     * @param param 参数
     */
    IPage<TeachEvaluationVO> getList(LessonParamDTO param);
}
