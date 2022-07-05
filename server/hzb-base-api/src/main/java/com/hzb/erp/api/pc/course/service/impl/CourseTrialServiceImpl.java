package com.hzb.erp.api.pc.course.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.pc.course.entity.CourseTrial;
import com.hzb.erp.api.pc.course.mapper.CourseTrialMapper;
import com.hzb.erp.api.pc.course.pojo.CourseTrialParamDTO;
import com.hzb.erp.api.pc.course.pojo.CourseTrialSaveDTO;
import com.hzb.erp.api.pc.course.pojo.CourseTrialVO;
import com.hzb.erp.api.pc.course.service.CourseTrialService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 课程体验卡 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class CourseTrialServiceImpl extends ServiceImpl<CourseTrialMapper, CourseTrial> implements CourseTrialService {

    @Override
    public IPage<CourseTrialVO> getList(CourseTrialParamDTO param) {
        return baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public List<CourseTrialVO> getAll(CourseTrialParamDTO param) {
        return baseMapper.getList(param);
    }

    @Override
    public boolean saveOrUpdateByDTO(CourseTrialSaveDTO dto) {
        CourseTrial item = new CourseTrial();
        BeanUtils.copyProperties(dto, item);
        item.setEditTime(LocalDateTime.now());
        return this.saveOrUpdate(item);
    }

}
