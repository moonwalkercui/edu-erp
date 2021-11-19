package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Classroom;
import com.hzb.erp.common.mapper.ClassroomMapper;
import com.hzb.erp.common.pojo.dto.ClassroomParamDTO;
import com.hzb.erp.common.pojo.dto.ClassroomSaveDTO;
import com.hzb.erp.common.pojo.vo.ClassroomVO;
import com.hzb.erp.common.service.ClassroomService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 教室 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class ClassroomServiceImpl extends ServiceImpl<ClassroomMapper, Classroom> implements ClassroomService {

    @Override
    public IPage<ClassroomVO> getList(ClassroomParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public Boolean saveOrUpdateByDTO(ClassroomSaveDTO dto) {
        Classroom item = new Classroom();
        BeanUtils.copyProperties(dto, item);
        return this.saveOrUpdate(item);
    }

}
