package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.StudentCreditLog;
import com.hzb.erp.common.enums.StudentCreditChangeTypeEnum;
import com.hzb.erp.common.pojo.dto.StudentCreditLogParamDTO;
import com.hzb.erp.common.pojo.vo.StudentCreditLogVO;
import com.hzb.erp.common.service.StudentCreditLogService;
import com.hzb.erp.common.mapper.StudentCreditLogMapper;
import com.hzb.erp.utils.EnumTools;
import org.springframework.stereotype.Service;

/**
 * 积分变动记录服务类
 */
@Service
public class StudentCreditLogServiceImpl extends ServiceImpl<StudentCreditLogMapper, StudentCreditLog> implements StudentCreditLogService{

    @Override
    public IPage<StudentCreditLogVO> getList(StudentCreditLogParamDTO param) {
        param.setChangeType(EnumTools.getCodeByDist(param.getChangeTypeText(), StudentCreditChangeTypeEnum.class));
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }
}




