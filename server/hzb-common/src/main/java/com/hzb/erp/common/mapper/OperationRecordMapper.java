package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.OperationRecord;
import com.hzb.erp.common.pojo.dto.OperationParamDTO;
import com.hzb.erp.common.pojo.vo.OperationRecordVO;
import org.springframework.stereotype.Repository;

/**
 * @Entity OperationRecord
 */
@Repository
public interface OperationRecordMapper extends BaseMapper<OperationRecord> {
    @DataScoped
    IPage<OperationRecordVO> getList(Page<Object> objectPage, OperationParamDTO param);
}




