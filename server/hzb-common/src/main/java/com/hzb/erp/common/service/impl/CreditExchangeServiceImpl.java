package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.*;
import com.hzb.erp.common.enums.MaterialRecordTypeEnum;
import com.hzb.erp.common.enums.StudentCreditChangeTypeEnum;
import com.hzb.erp.common.enums.SwitchEnum;
import com.hzb.erp.common.enums.VerifyStateEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.CreditMallMapper;
import com.hzb.erp.common.pojo.dto.AuditParamDTO;
import com.hzb.erp.common.pojo.dto.CreditExchangeParamDTO;
import com.hzb.erp.common.pojo.dto.CreditMallParamDTO;
import com.hzb.erp.common.pojo.vo.CreditExchangeVO;
import com.hzb.erp.common.service.CreditExchangeService;
import com.hzb.erp.common.mapper.CreditExchangeMapper;
import com.hzb.erp.common.service.StudentService;
import com.hzb.erp.utils.EnumTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 积分商城兑换记录
 */
@Service
public class CreditExchangeServiceImpl extends ServiceImpl<CreditExchangeMapper, CreditExchange> implements CreditExchangeService{
    @Autowired
    private StudentService studentService;

    @Override
    public IPage<CreditExchangeVO> getList(CreditExchangeParamDTO param) {
        param.setVerifyState(EnumTools.getCodeByDist(param.getVerifyStateText(), VerifyStateEnum.class));
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public boolean exchangeAudit(AuditParamDTO auditDTO) {
        List<CreditExchange> list = this.listByIds(auditDTO.getIds());
        for (CreditExchange item : list) {
            if(!VerifyStateEnum.APPLY.equals(item.getVerifyState()) ) {
                continue;
            }
            item.setVerifyState(EnumTools.getByCode(auditDTO.getVerifyState(), VerifyStateEnum.class));
            item.setVerifyStaff(auditDTO.getStaffId());
            item.setVerifyTime(LocalDateTime.now());
            item.setVerifyRemark(auditDTO.getVerifyRemark());

            if(VerifyStateEnum.REJECT.getCode() == auditDTO.getVerifyState()) {
                // 返还积分
                StudentCreditLog creditLog = new StudentCreditLog();
                creditLog.setStudentId(item.getStudentId());
                creditLog.setCredit(item.getCredit());
                creditLog.setChangeType(StudentCreditChangeTypeEnum.CREDIT_EXCHANGE_FAIL);
                creditLog.setSourceId(item.getId());
                studentService.incCredit(creditLog);
            }
        }
        return this.updateBatchById(list);
    }

}
