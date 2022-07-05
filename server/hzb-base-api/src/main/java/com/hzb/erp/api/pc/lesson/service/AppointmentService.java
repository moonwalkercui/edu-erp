package com.hzb.erp.api.pc.lesson.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.lesson.entity.Appointment;
import com.hzb.erp.api.pc.lesson.pojo.AppointmentParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.AppointmentVO;

import java.util.List;

/**
 * <p>
 * 试听预约记录 服务类
 * </p>
 *
 * @author Ryan
 */
public interface AppointmentService extends IService<Appointment> {

    /**
    * 新增
    * */
    Appointment addOne(Long studentId, Long lessonId);
    /**
    * 带分页的列表
    * */
    IPage<AppointmentVO> getList(AppointmentParamDTO param);

    /**
    * 审核
    * */
    Boolean handleAudit(List<Long> ids , Boolean state, Long auditorId);
}
