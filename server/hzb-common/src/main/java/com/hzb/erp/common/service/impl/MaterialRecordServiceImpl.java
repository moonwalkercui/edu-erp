package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.MaterialRecord;
import com.hzb.erp.common.enums.MaterialRecordTypeEnum;
import com.hzb.erp.common.mapper.MaterialRecordMapper;
import com.hzb.erp.common.pojo.dto.MaterialRecordParamDTO;
import com.hzb.erp.common.pojo.vo.MaterialRecordVO;
import com.hzb.erp.common.service.MaterialRecordService;
import com.hzb.erp.utils.EnumTools;
import org.springframework.stereotype.Service;

/**
 * 物料出入库记录
 */
@Service
public class MaterialRecordServiceImpl extends ServiceImpl<MaterialRecordMapper, MaterialRecord> implements MaterialRecordService{
    @Override
    public IPage<MaterialRecordVO> getList(MaterialRecordParamDTO param) {
        param.setChangeType(EnumTools.getCodeByDist(param.getChangeTypeText(), MaterialRecordTypeEnum.class));
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }
}




