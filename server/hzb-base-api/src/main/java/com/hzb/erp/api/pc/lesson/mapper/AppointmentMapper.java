package com.hzb.erp.api.pc.lesson.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.api.pc.lesson.entity.Appointment;
import com.hzb.erp.api.pc.lesson.pojo.AppointmentParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.AppointmentVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 试听预约记录 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {
    IPage<AppointmentVO> getList(Page<?> page, AppointmentParamDTO param);
}
