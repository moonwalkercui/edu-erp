package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.SysLog;
import com.hzb.erp.common.pojo.SysLogParamDTO;
import com.hzb.erp.common.pojo.SysLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 操作日志 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
    @DataScoped
    IPage<SysLogVO> getList(Page<?> page, SysLogParamDTO param);
}
