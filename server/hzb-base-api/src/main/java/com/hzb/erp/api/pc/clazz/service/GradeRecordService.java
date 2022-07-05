package com.hzb.erp.api.pc.clazz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.clazz.entity.GradeRecord;
import com.hzb.erp.api.pc.clazz.pojo.GradeParamDTO;
import com.hzb.erp.api.pc.clazz.pojo.GradeRecordSaveDTO;
import com.hzb.erp.api.pc.clazz.pojo.GradeRecordVO;

/**
 * <p>
 * 成绩单学生分数 服务类
 * </p>
 *
 * @author Ryan
 */
public interface GradeRecordService extends IService<GradeRecord> {
    IPage<GradeRecordVO> getList(GradeParamDTO param);

    Boolean saveOrUpdateByDTO(GradeRecordSaveDTO dto);
}
