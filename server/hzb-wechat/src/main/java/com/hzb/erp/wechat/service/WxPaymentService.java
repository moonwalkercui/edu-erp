package com.hzb.erp.wechat.service;

import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.hzb.erp.wechat.entiry.WxOrder;
import com.hzb.erp.wechat.entiry.WxOrderRefund;
import org.springframework.stereotype.Service;

/**
 * 支付、退款相关服务
 * @author Ryan 541720500@qq.com
 * gitee.com/binary/weixin-java-mp-demo-springboot
 */
@Service
public interface WxPaymentService {

    /**
     * 拼接支付参数
     */
    WxPayUnifiedOrderRequest buildPayParamByOrder(WxOrder order, String openid, String tradeType);

    /**
     * 拼接退款参数
     */
    WxPayRefundRequest buildRefundParamByOrderRefund(WxOrderRefund orderRefund);

    /**
    * 支付回调
    * */
    void payNotify(String xmlData) throws WxPayException;

    /**
     * 退款回调
     * */
    void refundNotify(String xmlData) throws WxPayException;
}
