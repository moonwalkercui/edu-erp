package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.*;
import com.hzb.erp.common.enums.MessageUserTypeEnum;
import com.hzb.erp.common.enums.SignStateEnum;
import com.hzb.erp.common.enums.SignTypeEnum;
import com.hzb.erp.common.enums.VerifyStateEnum;
import com.hzb.erp.common.mapper.AppointmentMapper;
import com.hzb.erp.common.pojo.dto.AppointmentParamDTO;
import com.hzb.erp.common.pojo.vo.AppointmentVO;
import com.hzb.erp.common.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.utils.EnumTools;
import com.hzb.erp.utils.SettingConstants;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.fenum.qual.AwtFlowLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 预约服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    @Autowired
    @Lazy
    private LessonService lessonService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SettingService settingService;

    @Autowired
    @Lazy
    private LessonStudentService lessonStudentService;

    @Autowired
    @Lazy
    private StudentService studentService;

    @Override
    public Appointment addOne(Long studentId, Long lessonId) {
        Lesson lesson = lessonService.getById(lessonId);
        Appointment apt = new Appointment();
        apt.setStudentId(studentId);
        apt.setCourseId(lesson.getCourseId());
        apt.setLessonId(lessonId);
        save(apt);
        return apt;
    }

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

            if (!state) {
                // 处理驳回
                QueryWrapper<LessonStudent> qw = new QueryWrapper<>();
                qw.eq("lesson_id", item.getLessonId());
                qw.eq("student_id", item.getStudentId());
                lessonStudentService.remove(qw);
                Lesson lesson = lessonService.getById(item.getLessonId());
                String msgTxt = "您预约的课程:" + lesson.descToString() + "被取消了。" + item.getVerifyRemark();

                if(!settingService.boolValue(SettingConstants.AUTO_JOIN_LESSON_BY_APPOINTMENT)) {
                    Student student = studentService.getById(item.getStudentId());
                    lessonStudentService.addOne(item.getLessonId(), student, SignStateEnum.NONE);
                }

                messageService.sendToStudent(
                        auditorId,
                        MessageUserTypeEnum.STAFF,
                        item.getStudentId(),
                        "你的课程预约被取消",
                        msgTxt);


            }
        }

        this.updateBatchById(list);
        if (lessonStudentList.size() > 0) {
            lessonStudentService.saveBatch(lessonStudentList);
        }

        return true;
    }
}
