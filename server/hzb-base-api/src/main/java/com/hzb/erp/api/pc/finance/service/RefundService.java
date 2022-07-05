package com.hzb.erp.api.pc.finance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.finance.entity.Refund;
import com.hzb.erp.api.pc.finance.pojo.RefundSaveDTO;

/**
 * <p>
 * 学员退款记录 服务类
 * </p>
 *
 * @author Ryan
 */
public interface RefundService extends IService<Refund> {

    Boolean saveOrUpdateByDTO(RefundSaveDTO dto, Long staffId);

    Refund getOneByStudentCourseId(Long scId);
}
