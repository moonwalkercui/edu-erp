package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.OperationRecord;
import com.hzb.erp.common.enums.OprationTypeEnum;

/**
 * 审核日志
 */
public interface OperationRecordService extends IService<OperationRecord> {
    Boolean addOne(Long itemId, OprationTypeEnum type, String state, String description, Long staffId);
}
