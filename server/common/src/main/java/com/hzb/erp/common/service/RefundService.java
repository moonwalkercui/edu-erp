package com.hzb.erp.common.service;

import com.hzb.erp.common.entity.Refund;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.pojo.dto.RefundSaveDTO;

/**
 * <p>
 * 学员退款记录 服务类
 * </p>
 *
 * @author Ryan
 */
public interface RefundService extends IService<Refund> {

    Boolean saveOrUpdateByDTO(RefundSaveDTO dto,Long staffId);

    Refund getOneByStudentCourseId(Long scId);
}
