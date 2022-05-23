package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Order;
import com.hzb.erp.common.pojo.dto.OrderConfirmDTO;


/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface OrderService extends IService<Order> {

    /**
    * 创建订单
    * */
    Order makeOrder(OrderConfirmDTO dto);

    /**
     * 处理支付成功通知
     * @param openid 支付人openid
     * @param outTradeNo 订单号
     * @param transactionId 微信交易号
     * @param totalFee 支付金额（分）
     * */
    void paySuccessNotify(String openid, String outTradeNo, String transactionId, Integer totalFee);
}
