package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.CourseTrialRecord;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.pojo.dto.CourseTrialRecordParamDTO;
import com.hzb.erp.common.pojo.vo.CourseTrialRecordVO;

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
