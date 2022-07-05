package com.hzb.erp.api.pc.clazz.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.pc.clazz.entity.GradeRecord;
import com.hzb.erp.api.pc.clazz.mapper.GradeRecordMapper;
import com.hzb.erp.api.pc.clazz.pojo.GradeParamDTO;
import com.hzb.erp.api.pc.clazz.pojo.GradeRecordSaveDTO;
import com.hzb.erp.api.pc.clazz.pojo.GradeRecordVO;
import com.hzb.erp.api.pc.clazz.service.GradeRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 成绩单学生分数 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class GradeRecordServiceImpl extends ServiceImpl<GradeRecordMapper, GradeRecord> implements GradeRecordService {

    @Override
    public IPage<GradeRecordVO> getList(GradeParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public Boolean saveOrUpdateByDTO(GradeRecordSaveDTO dto) {
        GradeRecord item = new GradeRecord();
        BeanUtils.copyProperties(dto, item);
        return this.saveOrUpdate(item);
    }
}
