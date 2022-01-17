package com.hzb.erp.common.pojo.vo;

import com.hzb.erp.common.entity.OperationRecord;
import lombok.Data;

@Data
public class OperationRecordVO extends OperationRecord {
    private String creatorName;
    private String comName;
    private String dptName;
}
