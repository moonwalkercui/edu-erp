package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.OperationRecord;
import com.hzb.erp.common.pojo.OperationParamDTO;
import com.hzb.erp.common.pojo.OperationRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity OperationRecord
 */
@Repository
@Mapper
public interface OperationRecordMapper extends BaseMapper<OperationRecord> {
    @DataScoped
    IPage<OperationRecordVO> getList(Page<Object> objectPage, OperationParamDTO param);
}




