package com.hzb.erp.api.pc.shop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.shop.entity.OrderRefund;
import com.hzb.erp.api.pc.shop.pojo.OrderRefundDTO;
import com.hzb.erp.api.pc.shop.pojo.OrderRefundParamDTO;
import com.hzb.erp.api.pc.shop.pojo.OrderRefundVo;

import java.math.BigDecimal;

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
     * 微信退款后执行
     * @param refundSn 本系统退款单号
     * @param tradeNo 第三方退款编号
     * @param refundMoney 退款金额
     * */
    void refundSuccessNotify(String refundSn, String tradeNo, BigDecimal refundMoney);

}
