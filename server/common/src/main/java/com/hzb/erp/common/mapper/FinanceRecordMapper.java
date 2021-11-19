package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.FinanceRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.pojo.dto.FinanceParamDTO;
import com.hzb.erp.common.pojo.vo.FinanceRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 财务记录表 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
public interface FinanceRecordMapper extends BaseMapper<FinanceRecord> {
    @DataScoped
    IPage<FinanceRecordVO> getList(Page<Object> objectPage, FinanceParamDTO param);
    @DataScoped
    List<FinanceRecordVO> getList(@Param("param") FinanceParamDTO param);
}
