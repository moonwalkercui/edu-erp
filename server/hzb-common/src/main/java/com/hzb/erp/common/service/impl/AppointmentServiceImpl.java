package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.Advertisement;
import com.hzb.erp.common.entity.Appointment;
import com.hzb.erp.common.enums.VerifyStateEnum;
import com.hzb.erp.common.mapper.AppointmentMapper;
import com.hzb.erp.common.pojo.dto.AppointmentParamDTO;
import com.hzb.erp.common.pojo.vo.AppointmentVO;
import com.hzb.erp.common.service.AppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * <p>
 * </p>
 *
 * @author Ryan
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    @Override
    public IPage<AppointmentVO> getList(AppointmentParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public Boolean handleAudit(List<Long> ids, Boolean state) {
        List<Appointment> list = this.listByIds(ids);
        for (Appointment item : list) {
            item.setVerifyState(VerifyStateEnum.APPLY);
            item.setVerifyTime(LocalDateTime.now());
        }

        // todo 同意后要添加学员到课次
        return this.updateBatchById(list);
        return null;
    }
}
