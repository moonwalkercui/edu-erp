package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Cashout;
import com.hzb.erp.common.enums.VerifyStateEnum;
import com.hzb.erp.common.pojo.dto.CashoutParamDTO;
import com.hzb.erp.common.pojo.dto.CashoutSaveDTO;
import com.hzb.erp.common.pojo.vo.CashoutVO;

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
