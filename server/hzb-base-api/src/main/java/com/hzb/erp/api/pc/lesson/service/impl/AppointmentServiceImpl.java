package com.hzb.erp.api.pc.lesson.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.pc.lesson.entity.Appointment;
import com.hzb.erp.api.pc.lesson.entity.Lesson;
import com.hzb.erp.api.pc.lesson.entity.LessonStudent;
import com.hzb.erp.api.pc.lesson.service.AppointmentService;
import com.hzb.erp.api.pc.lesson.service.LessonService;
import com.hzb.erp.api.pc.lesson.service.LessonStudentService;
import com.hzb.erp.api.pc.student.service.StudentService;
import com.hzb.erp.api.pc.sys.service.MessageService;
import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.common.enums.MessageUserTypeEnum;
import com.hzb.erp.common.enums.SignStateEnum;
import com.hzb.erp.common.enums.VerifyStateEnum;
import com.hzb.erp.api.pc.lesson.mapper.AppointmentMapper;
import com.hzb.erp.api.pc.lesson.pojo.AppointmentParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.AppointmentVO;
import com.hzb.erp.common.service.*;
import com.hzb.erp.service.enums.SettingNameEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        for (Appointment item : list) {
            item.setVerifyState(state ? VerifyStateEnum.APPROVE : VerifyStateEnum.REJECT);
            item.setVerifyTime(LocalDateTime.now());
            item.setVerifyStaff(auditorId);

            Lesson lesson = lessonService.getById(item.getLessonId());
            if (!state) {
                // 处理驳回
                QueryWrapper<LessonStudent> qw = new QueryWrapper<>();
                qw.eq("lesson_id", item.getLessonId());
                qw.eq("student_id", item.getStudentId());
                lessonStudentService.remove(qw);
                String msgTxt = "您预约的课程:" + lesson.descToString() + "被取消了。" + item.getVerifyRemark();
                messageService.sendToStudent(
                        auditorId,
                        MessageUserTypeEnum.STAFF,
                        item.getStudentId(),
                        "你的课程预约被取消",
                        msgTxt);

            } else {
                if(!settingService.boolValue(SettingNameEnum.AUTO_JOIN_LESSON_BY_APPOINTMENT.getCode())) {
                    Student student = studentService.getById(item.getStudentId());
                    lessonStudentService.addOne(item.getLessonId(), student, SignStateEnum.NONE);
                }
            }
        }
        this.updateBatchById(list);
        return true;
    }
}
