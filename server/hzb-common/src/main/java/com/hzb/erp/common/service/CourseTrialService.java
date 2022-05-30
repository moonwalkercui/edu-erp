package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.CourseTrial;
import com.hzb.erp.common.pojo.dto.CourseTrialParamDTO;
import com.hzb.erp.common.pojo.dto.CourseTrialSaveDTO;
import com.hzb.erp.common.pojo.vo.CourseTrialVO;

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
