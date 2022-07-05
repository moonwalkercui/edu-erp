package com.hzb.erp.api.pc.creditMall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.pc.creditMall.entity.CreditExchange;
import com.hzb.erp.api.pc.student.entity.StudentCreditLog;
import com.hzb.erp.common.enums.StudentCreditChangeTypeEnum;
import com.hzb.erp.common.enums.VerifyStateEnum;
import com.hzb.erp.common.pojo.AuditParamDTO;
import com.hzb.erp.api.pc.creditMall.pojo.CreditExchangeParamDTO;
import com.hzb.erp.api.pc.creditMall.pojo.CreditExchangeVO;
import com.hzb.erp.api.pc.creditMall.service.CreditExchangeService;
import com.hzb.erp.api.pc.creditMall.mapper.CreditExchangeMapper;
import com.hzb.erp.api.pc.student.service.StudentService;
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
