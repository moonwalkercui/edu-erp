package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Grade;
import com.hzb.erp.common.mapper.GradeMapper;
import com.hzb.erp.common.pojo.dto.GradeSaveDTO;
import com.hzb.erp.common.pojo.dto.GradeParamDTO;
import com.hzb.erp.common.pojo.vo.GradeVO;
import com.hzb.erp.common.service.GradeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 成绩单 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Override
    public IPage<GradeVO> getList(GradeParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public List<GradeVO> getAll(GradeParamDTO param) {
        return this.baseMapper.getList(param);
    }

    @Override
    public Boolean saveOrUpdateByDTO(GradeSaveDTO dto) {
        Grade item = new Grade();
        BeanUtils.copyProperties(dto, item);
        return this.saveOrUpdate(item);
    }
}
