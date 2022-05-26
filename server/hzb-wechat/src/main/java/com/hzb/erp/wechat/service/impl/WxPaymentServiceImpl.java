package com.hzb.erp.wechat.service.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.hzb.erp.common.entity.Order;
import com.hzb.erp.common.entity.OrderRefund;
import com.hzb.erp.common.enums.OrderRefundStateEnum;
import com.hzb.erp.common.enums.OrderStateEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.OrderItemMapper;
import com.hzb.erp.common.service.OrderRefundService;
import com.hzb.erp.common.service.OrderService;
import com.hzb.erp.utils.IpUtil;
import com.hzb.erp.utils.RequestUtil;
import com.hzb.erp.wechat.service.WxPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p> </p>
 *
 * @author Ryan 541720500@qq.com
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WxPaymentServiceImpl implements WxPaymentService {

    private final WxPayService wxPayService;
    private final OrderItemMapper orderItemMapper;
    private final OrderService orderService;
    private final OrderRefundService orderRefundService;

    @Override
    public WxPayUnifiedOrderRequest buildPayParamByOrder(Order order, String openid, String tradeType) {
        Order findOrder = orderService.getById(order.getId());
        if(!findOrder.getState().equals(OrderStateEnum.UNPAID)) {
            throw new BizException(findOrder.getState().getDist() + "的订单无法支付");
        }
        String notifyUrl;
        String createIp;
        try {
            HttpServletRequest request = RequestUtil.getRequest();
            notifyUrl = getPayNotifyUrl(request);
            createIp = IpUtil.getIpAddr(request);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        String itemNames = orderItemMapper.getNamesByOrderId(order.getId());

        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
//        BigDecimal payMoney = order.getPayMoney() == null ? BigDecimal.ZERO : order.getPayMoney();
        BigDecimal payMoney = new BigDecimal("0.01"); // 测试用1分钱 todo
        orderRequest.setOpenid(openid);
        orderRequest.setBody(StringUtils.isBlank(itemNames) ? "订单号"+order.getSn() : itemNames);
        orderRequest.setOutTradeNo(order.getSn());
        orderRequest.setTotalFee((new BigDecimal(100).multiply(payMoney).intValue()));
        orderRequest.setSpbillCreateIp(createIp);
        orderRequest.setNotifyUrl(notifyUrl);
        orderRequest.setTradeType(tradeType);
        return orderRequest;
    }

    @Override
    public WxPayRefundRequest buildRefundParamByOrderRefund(OrderRefund orderRefund) {
        OrderRefund findRefund = orderRefundService.getById(orderRefund.getId());
        if(!findRefund.getState().equals(OrderRefundStateEnum.APPLY)) {
            throw new BizException(findRefund.getState().getDist() + "的退款单无法退款");
        }

        String notifyUrl;
        String createIp;
        try {
            HttpServletRequest request = RequestUtil.getRequest();
            notifyUrl = getRefundNotifyUrl(request);
            createIp = IpUtil.getIpAddr(request);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        WxPayRefundRequest refundRequest = new WxPayRefundRequest();
        refundRequest.setNotifyUrl(notifyUrl);
        // todo
        return refundRequest;
    }

    @Override
    public void payNotify(String xmlData) throws WxPayException {
        log.info("==============支付回调===================");
        final WxPayOrderNotifyResult result = this.wxPayService.parseOrderNotifyResult(xmlData);
        log.info(String.valueOf(result));
        orderService.paySuccessNotify(result.getOpenid(), result.getOutTradeNo(), result.getTransactionId(), result.getTotalFee());
        log.info("==============支付回调结束===================");
    }

    @Override
    public void refundNotify(String xmlData) throws WxPayException {
        log.info("==============退款回调===================");
        final WxPayRefundNotifyResult result = this.wxPayService.parseRefundNotifyResult(xmlData);
        log.info(String.valueOf(result));
        WxPayRefundNotifyResult.ReqInfo reqInfo = result.getReqInfo();
        orderRefundService.refundSuccessNotify(reqInfo.getOutRefundNo(), reqInfo.getRefundId(), new BigDecimal(reqInfo.getTotalFee() / 100));
    }

    private String getPayNotifyUrl(HttpServletRequest request) throws MalformedURLException {
        URL requestUrl = new URL(request.getRequestURL().toString());
        return requestUrl.getHost() + "/wx/pay/handleNotify";
    }

    private String getRefundNotifyUrl(HttpServletRequest request) throws MalformedURLException {
        URL requestUrl = new URL(request.getRequestURL().toString());
        return requestUrl.getHost() + "/wx/pay/refundNotify";
    }
}
