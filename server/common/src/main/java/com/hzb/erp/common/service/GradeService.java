package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Grade;
import com.hzb.erp.common.pojo.dto.GradeParamDTO;
import com.hzb.erp.common.pojo.dto.GradeSaveDTO;
import com.hzb.erp.common.pojo.vo.GradeVO;

import java.util.List;

/**
 * <p>
 * 成绩单 服务类
 * </p>
 *
 * @author Ryan
 */
public interface GradeService extends IService<Grade> {
    IPage<GradeVO> getList(GradeParamDTO param);

    List<GradeVO> getAll(GradeParamDTO param);

    Boolean saveOrUpdateByDTO(GradeSaveDTO dto);
}
