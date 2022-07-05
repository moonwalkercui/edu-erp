package com.hzb.erp.api.pc.lesson.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.lesson.entity.TeachEvaluation;
import com.hzb.erp.api.pc.lesson.pojo.LessonParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.TeachEvaluationVO;

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
