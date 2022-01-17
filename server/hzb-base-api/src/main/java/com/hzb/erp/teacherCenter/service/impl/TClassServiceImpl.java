package com.hzb.erp.teacherCenter.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Clazz;
import com.hzb.erp.common.mapper.ClazzMapper;
import com.hzb.erp.common.pojo.dto.ClassParamDTO;
import com.hzb.erp.common.pojo.vo.ClassVO;
import com.hzb.erp.service.UserAuthService;
import com.hzb.erp.teacherCenter.service.TClassService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课次表 服务实现类
 * </p>
 *
 * @author 541720500@qq.com
 */
@Service
public class TClassServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements TClassService {

    @Override
    public IPage<ClassVO> getList(ClassParamDTO param) {
        param.setRelTeacherId(UserAuthService.getCurrentUserId());
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }
}
