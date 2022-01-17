package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hzb.erp.common.entity.Appointment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.pojo.dto.AppointmentParamDTO;
import com.hzb.erp.common.pojo.vo.AppointmentVO;

/**
 * <p>
 * 试听预约记录 服务类
 * </p>
 *
 * @author Ryan
 */
public interface AppointmentService extends IService<Appointment> {
    // 带分页的列表
    IPage<AppointmentVO> getList(AppointmentParamDTO param);
}
