package com.hzb.erp.api.pc.clazz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.clazz.entity.Grade;
import com.hzb.erp.api.pc.clazz.pojo.GradeParamDTO;
import com.hzb.erp.api.pc.clazz.pojo.GradeSaveDTO;
import com.hzb.erp.api.pc.clazz.pojo.GradeVO;

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
