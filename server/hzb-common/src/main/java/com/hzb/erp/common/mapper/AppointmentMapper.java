package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.Appointment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.pojo.dto.AppointmentParamDTO;
import com.hzb.erp.common.pojo.dto.StudentCourseParamDTO;
import com.hzb.erp.common.pojo.vo.AppointmentVO;

/**
 * <p>
 * 试听预约记录 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
public interface AppointmentMapper extends BaseMapper<Appointment> {
    IPage<AppointmentVO> getList(Page<?> page, AppointmentParamDTO param);
}
