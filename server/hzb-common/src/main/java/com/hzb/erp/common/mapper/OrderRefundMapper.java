package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.OrderRefund;
import com.hzb.erp.common.pojo.dto.OrderRefundParamDTO;
import com.hzb.erp.common.pojo.vo.OrderRefundVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单退款 mapper
 */
public interface OrderRefundMapper extends BaseMapper<OrderRefund> {

    IPage<OrderRefundVo> getList(Page<Object> objectPage, OrderRefundParamDTO param);
    List<OrderRefundVo> getList(@Param("param") OrderRefundParamDTO param);
}
