package com.hzb.erp.api.pc.lesson.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.pc.lesson.entity.Subject;
import com.hzb.erp.api.pc.lesson.mapper.SubjectMapper;
import com.hzb.erp.common.pojo.CommonParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.SubjectSaveDTO;
import com.hzb.erp.common.pojo.SubjectVO;
import com.hzb.erp.api.pc.lesson.service.SubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 科目表 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public IPage<SubjectVO> getList(CommonParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public Boolean saveOrUpdateByDTO(SubjectSaveDTO dto) {
        Subject item = new Subject();
        BeanUtils.copyProperties(dto, item);
        return this.saveOrUpdate(item);
    }
}
