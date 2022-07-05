package com.hzb.erp.api.pc.lesson.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.pc.lesson.entity.Homework;
import com.hzb.erp.api.pc.lesson.mapper.HomeworkMapper;
import com.hzb.erp.api.pc.lesson.pojo.HomeworkParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.HomeworkSaveDTO;
import com.hzb.erp.api.pc.lesson.pojo.HomeworkVO;
import com.hzb.erp.api.pc.lesson.service.HomeworkService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 作业 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class HomeworkServiceImpl extends ServiceImpl<HomeworkMapper, Homework> implements HomeworkService {

    @Override
    public IPage<HomeworkVO> getList(HomeworkParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public List<HomeworkVO> getAll(HomeworkParamDTO param) {
        return this.baseMapper.getList(param);
    }

    @Override
    public Boolean saveOrUpdateByDTO(HomeworkSaveDTO dto) {
        Homework item = new Homework();
        BeanUtils.copyProperties(dto, item);
        return this.saveOrUpdate(item);
    }

    @Override
    public HomeworkVO getInfo(Long id, Long studentId) {
        return baseMapper.getInfo(id, studentId);
    }

}
