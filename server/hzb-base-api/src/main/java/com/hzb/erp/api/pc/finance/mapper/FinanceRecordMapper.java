package com.hzb.erp.api.pc.finance.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.api.pc.finance.entity.FinanceRecord;
import com.hzb.erp.api.pc.finance.pojo.FinanceParamDTO;
import com.hzb.erp.api.pc.finance.pojo.FinanceRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 财务记录表 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface FinanceRecordMapper extends BaseMapper<FinanceRecord> {
    @DataScoped
    IPage<FinanceRecordVO> getList(Page<Object> objectPage, FinanceParamDTO param);

    @DataScoped
    List<FinanceRecordVO> getList(@Param("param") FinanceParamDTO param);
}
