package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.OperationLog;
import com.hzb.erp.common.pojo.dto.OperationLogParamDTO;
import com.hzb.erp.common.pojo.vo.OperationLogVO;

/**
 * <p>
 * 操作日志 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
public interface OperationLogMapper extends BaseMapper<OperationLog> {
    IPage<OperationLogVO> getList(Page<?> page, OperationLogParamDTO param);
}
