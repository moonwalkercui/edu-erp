package com.hzb.erp.api.pc.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.finance.entity.Cashout;
import com.hzb.erp.common.enums.VerifyStateEnum;
import com.hzb.erp.api.pc.finance.pojo.CashoutParamDTO;
import com.hzb.erp.api.pc.finance.pojo.CashoutSaveDTO;
import com.hzb.erp.api.pc.finance.pojo.CashoutVO;

import java.util.List;

/**
 * <p>
 * 系统设置表 服务类
 * </p>
 *
 * @author Ryan
 */
public interface CashoutService extends IService<Cashout> {
    IPage<CashoutVO> getList(CashoutParamDTO param);

    Boolean saveOrUpdateByDTO(CashoutSaveDTO dto);

    boolean cancel(Long id);

    Boolean changeState(List<Long> ids, VerifyStateEnum state, String remark, Long staffId);

    void exportExcel(CashoutParamDTO param);
}
