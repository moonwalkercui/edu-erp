package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.SysLog;
import com.hzb.erp.common.pojo.dto.SysLogParamDTO;
import com.hzb.erp.common.pojo.vo.SysLogVO;

/**
 * <p>
 * 操作日志 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
public interface SysLogMapper extends BaseMapper<SysLog> {
    @DataScoped
    IPage<SysLogVO> getList(Page<?> page, SysLogParamDTO param);
}
