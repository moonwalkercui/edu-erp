package com.hzb.erp.api.pc.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.mobile.student.pojo.dto.StudentLeaveDTO;
import com.hzb.erp.api.pc.lesson.entity.Lesson;
import com.hzb.erp.api.pc.lesson.entity.LessonStudent;
import com.hzb.erp.api.pc.lesson.mapper.LessonMapper;
import com.hzb.erp.api.pc.student.entity.StudentLeave;
import com.hzb.erp.common.enums.SignStateEnum;
import com.hzb.erp.common.enums.StudentLeaveSateEnum;
import com.hzb.erp.api.pc.student.mapper.StudentLeaveMapper;
import com.hzb.erp.api.pc.student.pojo.StudentLeaveParamDTO;
import com.hzb.erp.api.pc.student.pojo.StudentLeaveVO;
import com.hzb.erp.api.pc.lesson.service.LessonStudentService;
import com.hzb.erp.api.pc.student.service.StudentLeaveService;
import com.hzb.erp.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 学员请假 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class StudentLeaveServiceImpl extends ServiceImpl<StudentLeaveMapper, StudentLeave> implements StudentLeaveService {

    @Autowired
    private LessonStudentService lessonStudentService;

    @Autowired
    private LessonMapper lessonMapper;

    @Override
    public IPage<StudentLeaveVO> getList(StudentLeaveParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }
//
//    @Override
//    public Boolean handle(List<Long> ids, VerifyStateEnum verify) {
//        QueryWrapper<StudentLeave> qw = new QueryWrapper<>();
//        qw.in("id", ids);
//        List<StudentLeave> list = this.list(qw);
//        for (StudentLeave item: list ) {
//            item.setVerifyState(verify);
//        }
//        return this.updateBatchById(list);
//    }//

    @Override
    @Transactional
    public Boolean cancel(List<Long> ids, Long staffId) {
        QueryWrapper<StudentLeave> qw = new QueryWrapper<>();
        qw.in("id", ids);
        List<StudentLeave> list = this.list(qw);
        List<LessonStudent> lsList = new ArrayList<>();

        for (StudentLeave item : list) {
            item.setState(StudentLeaveSateEnum.CANCELED);
            item.setVerifyStaff(staffId);
            item.setVerifyTime(LocalDateTime.now());

            // 取消学员的请假考勤状态
            LessonStudent ls = lessonStudentService.getByLessonIdAndStudentId(item.getLessonId(), item.getStudentId());
            if (ls != null && SignStateEnum.LEAVE.equals(ls.getSignState())) {
                ls.setSignState(SignStateEnum.NONE);
                lsList.add(ls);
            }

        }
        if (lsList.size() > 0) {
            lessonStudentService.updateBatchById(lsList);
        }
        return this.updateBatchById(list);
    }

    @Override
    public boolean handleLeave(StudentLeaveDTO dto) {
        LambdaQueryWrapper<StudentLeave> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StudentLeave::getStudentId, dto.getStudentId());
        wrapper.eq(StudentLeave::getLessonId, dto.getLessonId());
        wrapper.last("limit 1");
        StudentLeave sl = this.baseMapper.selectOne(wrapper);
        if(sl != null) {
            throw new BizException("请勿重复请假");
        }
        Lesson lesson = lessonMapper.selectById(dto.getLessonId());
        LocalDateTime lessonTime = LocalDateTime.of(lesson.getDate(), lesson.getStartTime());
        if(LocalDateTime.now().isAfter(lessonTime)) {
            throw new BizException("已开始的课不能请假");
        }
        StudentLeave newLeave = new StudentLeave();
        newLeave.setAddTime(LocalDateTime.now());
        newLeave.setStudentId(dto.getStudentId());
        newLeave.setLessonId(dto.getLessonId());
        newLeave.setReason(dto.getReason());
        newLeave.setState(StudentLeaveSateEnum.SUCCESS);
        return save(newLeave);
    }
}
