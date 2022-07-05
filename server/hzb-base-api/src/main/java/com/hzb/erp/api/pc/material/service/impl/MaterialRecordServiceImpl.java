package com.hzb.erp.api.pc.material.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.pc.material.entity.MaterialRecord;
import com.hzb.erp.api.pc.material.service.MaterialRecordService;
import com.hzb.erp.common.enums.MaterialRecordTypeEnum;
import com.hzb.erp.api.pc.material.mapper.MaterialRecordMapper;
import com.hzb.erp.api.pc.material.pojo.MaterialRecordParamDTO;
import com.hzb.erp.api.pc.material.pojo.MaterialRecordVO;
import com.hzb.erp.utils.EnumTools;
import org.springframework.stereotype.Service;

/**
 * 物料出入库记录
 */
@Service
public class MaterialRecordServiceImpl extends ServiceImpl<MaterialRecordMapper, MaterialRecord> implements MaterialRecordService {
    @Override
    public IPage<MaterialRecordVO> getList(MaterialRecordParamDTO param) {
        param.setChangeType(EnumTools.getCodeByDist(param.getChangeTypeText(), MaterialRecordTypeEnum.class));
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }
}




