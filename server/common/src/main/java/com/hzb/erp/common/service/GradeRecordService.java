package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.GradeRecord;
import com.hzb.erp.common.pojo.dto.GradeParamDTO;
import com.hzb.erp.common.pojo.dto.GradeRecordSaveDTO;
import com.hzb.erp.common.pojo.vo.GradeRecordVO;

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
