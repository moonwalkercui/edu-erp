package com.hzb.erp.api.pc.material.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.material.entity.MaterialRecord;
import com.hzb.erp.api.pc.material.pojo.MaterialRecordParamDTO;
import com.hzb.erp.api.pc.material.pojo.MaterialRecordVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Entity com.hzb.erp.api.adminCenter.material.entity.MaterialRecord
 */
@Mapper
public interface MaterialRecordMapper extends BaseMapper<MaterialRecord> {
    IPage<MaterialRecordVO> getList(Page<Object> objectPage, MaterialRecordParamDTO param);
}




