package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.*;
import com.hzb.erp.common.enums.SignStateEnum;
import com.hzb.erp.common.enums.SignTypeEnum;
import com.hzb.erp.common.enums.VerifyStateEnum;
import com.hzb.erp.common.mapper.AppointmentMapper;
import com.hzb.erp.common.pojo.dto.AppointmentParamDTO;
import com.hzb.erp.common.pojo.vo.AppointmentVO;
import com.hzb.erp.common.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.utils.EnumTools;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *    预约服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    private final StudentService studentService;
    private final LessonStudentService lessonStudentService;

    @Override
    public IPage<AppointmentVO> getList(AppointmentParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public Boolean handleAudit(List<Long> ids, Boolean state, Long auditorId) {
        List<Appointment> list = this.listByIds(ids);
        List<LessonStudent> lessonStudentList = new ArrayList<>();
        for (Appointment item : list) {
            item.setVerifyState(state ? VerifyStateEnum.APPROVE : VerifyStateEnum.REJECT);
            item.setVerifyTime(LocalDateTime.now());
            item.setVerifyStaff(auditorId);

            if(state) {
                Student student = studentService.getById(item.getStudentId());
                LessonStudent newLs = new LessonStudent();
                newLs.setLessonId(item.getLessonId());
                newLs.setClassId(null);
                newLs.setStudentId(item.getStudentId());
                newLs.setSignState(SignStateEnum.NONE);
                newLs.setConsumeCourseId(item.getCourseId());
                newLs.setCounselor(student.getCounselor());
                lessonStudentList.add(newLs);
            }
        }

        this.updateBatchById(list);
        if(lessonStudentList.size() > 0) {
            lessonStudentService.saveBatch(lessonStudentList);
        }

        return true;
    }
}
