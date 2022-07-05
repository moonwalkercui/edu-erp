package com.hzb.erp.api.pc.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.finance.entity.FinanceRecord;
import com.hzb.erp.common.enums.FinanceStateEnum;
import com.hzb.erp.api.pc.finance.pojo.FinanceParamDTO;
import com.hzb.erp.api.pc.finance.pojo.FinanceRecordVO;

import java.util.List;

/**
 * <p>
 * 财务记录表 服务类
 * </p>
 *
 * @author Ryan
 */
public interface FinanceRecordService extends IService<FinanceRecord> {

    IPage<FinanceRecordVO> getList(FinanceParamDTO param);

    /**
     * 处理审核状态变更
     */
    Boolean changeState(List<Long> ids, FinanceStateEnum reject, String remark, Long staffId);

    /**
     * 课时变动日志
     */
    void makeLessonCountLog(List<Long> ids, FinanceStateEnum state, Long staffId);

    void exportExcel(FinanceParamDTO param);
}
