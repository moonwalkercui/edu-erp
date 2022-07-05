package com.hzb.erp.api.pc.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.api.pc.finance.entity.Cashout;
import com.hzb.erp.api.pc.finance.pojo.CashoutParamDTO;
import com.hzb.erp.api.pc.finance.pojo.CashoutVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity generator.domain.Cashout
 */
@Repository
@Mapper
public interface CashoutMapper extends BaseMapper<Cashout> {
    @DataScoped
    IPage<CashoutVO> getList(Page<Object> objectPage, CashoutParamDTO param);

    @DataScoped
    List<CashoutVO> getList(@Param("param") CashoutParamDTO param);

    CashoutVO getInfo(Long id);

}




