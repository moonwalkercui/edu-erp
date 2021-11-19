package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.FinanceRecord;
import com.hzb.erp.common.enums.FinanceStateEnum;
import com.hzb.erp.common.pojo.dto.FinanceParamDTO;
import com.hzb.erp.common.pojo.vo.FinanceRecordVO;

import java.util.List;

/**
 * <p>
 * 财务记录表 服务类
 * </p>
 *
 * @author Ryan
 */
public interface FinanceRecordService extends IService<FinanceRecord> {

    /**
     * amount在收入时是正数 支出是负数
     */
    FinanceRecord addOne(FinanceRecord record);

    IPage<FinanceRecordVO> getList(FinanceParamDTO param);

    Boolean changeState(List<Long> ids, FinanceStateEnum reject, String remark, Long staffId);

    void makeLessonCountLog(List<Long> ids, FinanceStateEnum state, Long staffId);

    void exportExcel(FinanceParamDTO param);
}
