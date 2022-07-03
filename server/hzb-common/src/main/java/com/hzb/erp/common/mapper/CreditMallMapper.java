package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.CreditMall;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.pojo.dto.CreditMallParamDTO;
import com.hzb.erp.common.pojo.vo.CreditMallVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.hzb.erp.common.entity.CreditMall
 */
public interface CreditMallMapper extends BaseMapper<CreditMall> {
    @DataScoped
    IPage<CreditMallVO> getList(Page<?> page, CreditMallParamDTO param);

    @DataScoped
    List<CreditMallVO> getList(@Param("param") CreditMallParamDTO param);

    CreditMallVO getInfo(Long id);
}




