package com.hzb.erp.api.pc.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.course.entity.CourseTrialRecord;
import com.hzb.erp.api.pc.course.pojo.CourseTrialRecordParamDTO;
import com.hzb.erp.api.pc.course.pojo.CourseTrialRecordVO;
import com.hzb.erp.api.pc.student.entity.Student;

import java.util.List;

/**
 * <p>
 * 课程体验卡 服务类
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface CourseTrialRecordService extends IService<CourseTrialRecord> {
    IPage<CourseTrialRecordVO> getList(CourseTrialRecordParamDTO param);
    List<CourseTrialRecordVO> getAll(CourseTrialRecordParamDTO param);

    /**
    * 领取体验卡
    * */
    boolean getOne(Long trialId, Student student);
}
