package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.OrderRefund;
import com.hzb.erp.common.pojo.dto.IdsAndContentDTO;
import com.hzb.erp.common.pojo.dto.OrderRefundDTO;
import com.hzb.erp.common.pojo.dto.OrderRefundParamDTO;
import com.hzb.erp.common.pojo.vo.OrderRefundVo;

/**
 * <p>
 * 订单退款 服务类
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface OrderRefundService extends IService<OrderRefund> {

    /**
    * 申请退款
    * */
    Boolean handleRefund(OrderRefundDTO dto);

    /**
     * 分页查询
     * */
    IPage<OrderRefundVo> getList(OrderRefundParamDTO param);

    /**
     * 同意退款
     * */
    Boolean handleApprove(IdsAndContentDTO dto, Long staffId);

    /**
     * 驳回退款
     * */
    Boolean handleReject(IdsAndContentDTO dto, Long staffId);
}
