package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Cashout;
import com.hzb.erp.common.enums.VerifyStateEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.CashoutMapper;
import com.hzb.erp.common.pojo.dto.CashoutParamDTO;
import com.hzb.erp.common.pojo.dto.CashoutSaveDTO;
import com.hzb.erp.common.pojo.vo.CashoutVO;
import com.hzb.erp.common.service.CashoutService;
import com.hzb.erp.service.ImportExportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 * 请款 服务实现类
 * </p>
 *
 * @author 541720500@qq.com
 */
@Service
public class CashoutServiceImpl extends ServiceImpl<CashoutMapper, Cashout> implements CashoutService {
    @Autowired
    private ImportExportService importExportService;

    @Override
    public IPage<CashoutVO> getList(CashoutParamDTO param) {
        return baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public Boolean saveOrUpdateByDTO(CashoutSaveDTO dto) {

        if (dto.getId() == null) {
            Cashout exist = getById(dto.getId());
            if (exist != null && !VerifyStateEnum.APPLY.equals(exist.getVerifyState())) {
                throw new BizException("不在申请中的请款无法修改");
            }
        }

        Cashout item = new Cashout();
        BeanUtils.copyProperties(dto, item);
        if (dto.getAttachFile() != null && dto.getAttachFile().size() > 0) {
            item.setAttachFile(StringUtils.join(dto.getAttachFile(), ","));
        } else {
            item.setAttachFile(null);
        }
        if (dto.getId() == null) {
            item.setVerifyState(VerifyStateEnum.APPLY);
        }
        return saveOrUpdate(item);
    }

    @Override
    public boolean cancel(Long id) {
        Cashout item = getById(id);
        if (!VerifyStateEnum.APPLY.equals(item.getVerifyState())) {
            throw new BizException("不在申请中的请款无法作废");
        }
        return removeById(item);
    }

    @Override
    public Boolean changeState(List<Long> ids, VerifyStateEnum state, String remark, Long staffId) {
        List<Cashout> list = this.listByIds(ids);
        for (Cashout item : list) {
            if (!VerifyStateEnum.APPLY.equals(item.getVerifyState())) {
                throw new BizException("已审核的项目无法重复审核");
            }
            item.setVerifyState(state);
            item.setVerifyStaff(staffId);
            item.setVerifyRemark(remark);
            item.setVerifyTime(LocalDateTime.now());
        }
        return updateBatchById(list);
    }

    @Override
    public void exportExcel(CashoutParamDTO param) {
        List<CashoutVO> list = this.baseMapper.getList(param);
        LinkedHashMap<String, String> header = new LinkedHashMap<String, String>() {{
            put("id", "系统编号");
            put("title", "请款项目");
            put("typeName", "款项类型");
            put("staffName", "申请人");
            put("payeeName", "收款人");
            put("amount", "请款金额");
            put("addTime", "申请时间");
            put("account", "收款账号");
            put("info", "请款说明");
            put("verifyState", "审核状态");
            put("verifyStaffName", "审核人");
            put("verifyTime", "审核时间");
            put("verifyRemark", "审核备注");
        }};
        importExportService.exportExcel(header, list, "请款记录表");
    }
}
