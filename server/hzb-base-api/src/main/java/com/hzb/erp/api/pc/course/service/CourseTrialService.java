package com.hzb.erp.api.pc.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.course.entity.CourseTrial;
import com.hzb.erp.api.pc.course.pojo.CourseTrialParamDTO;
import com.hzb.erp.api.pc.course.pojo.CourseTrialSaveDTO;
import com.hzb.erp.api.pc.course.pojo.CourseTrialVO;

import java.util.List;

/**
 * <p>
 * 课程体验卡 服务类
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface CourseTrialService extends IService<CourseTrial> {
    IPage<CourseTrialVO> getList(CourseTrialParamDTO param);
    List<CourseTrialVO> getAll(CourseTrialParamDTO param);

    /**
    * 保存或新增
    * */
    boolean saveOrUpdateByDTO(CourseTrialSaveDTO dto);
}
