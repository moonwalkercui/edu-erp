package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.MaterialRecord;
import com.hzb.erp.common.pojo.dto.MaterialRecordParamDTO;
import com.hzb.erp.common.pojo.vo.MaterialRecordVO;

/**
 * @Entity com.hzb.erp.common.entity.MaterialRecord
 */
public interface MaterialRecordMapper extends BaseMapper<MaterialRecord> {
    IPage<MaterialRecordVO> getList(Page<Object> objectPage, MaterialRecordParamDTO param);
}




