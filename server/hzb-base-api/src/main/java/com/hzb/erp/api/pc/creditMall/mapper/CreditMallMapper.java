package com.hzb.erp.api.pc.creditMall.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.api.pc.creditMall.entity.CreditMall;
import com.hzb.erp.api.pc.creditMall.pojo.CreditMallParamDTO;
import com.hzb.erp.api.pc.creditMall.pojo.CreditMallVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.hzb.erp.api.adminCenter.creditMall.entity.CreditMall
 */
@Mapper
public interface CreditMallMapper extends BaseMapper<CreditMall> {
    @DataScoped
    IPage<CreditMallVO> getList(Page<?> page, CreditMallParamDTO param);

    @DataScoped
    List<CreditMallVO> getList(@Param("param") CreditMallParamDTO param);

    CreditMallVO getInfo(Long id);
}




