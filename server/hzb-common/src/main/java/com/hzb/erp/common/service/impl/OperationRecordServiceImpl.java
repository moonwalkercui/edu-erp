package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hzb.erp.common.entity.OperationRecord;
import com.hzb.erp.common.enums.OprationTypeEnum;
import com.hzb.erp.common.mapper.OperationRecordMapper;
import com.hzb.erp.common.service.OperationRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 *
 */
@Service
public class OperationRecordServiceImpl extends ServiceImpl<OperationRecordMapper, OperationRecord>
        implements OperationRecordService {

    @Override
    public Boolean addOne(Long itemId, OprationTypeEnum type, String state, String description, Long staffId) {
        OperationRecord item = new OperationRecord();
        item.setType(type);
        item.setItemId(itemId);
        item.setState(state);
        item.setContent(description);
        item.setAddTime(LocalDateTime.now());
        item.setCreator(staffId);
        return save(item);
    }

}




