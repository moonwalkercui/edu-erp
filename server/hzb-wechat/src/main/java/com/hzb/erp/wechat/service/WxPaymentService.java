package com.hzb.erp.wechat.service;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.hzb.erp.common.entity.Order;
import com.hzb.erp.common.entity.OrderRefund;
import com.hzb.erp.common.mapper.CourseMapper;
import com.hzb.erp.common.mapper.OrderItemMapper;
import com.hzb.erp.common.mapper.OrderMapper;
import com.hzb.erp.utils.IpUtil;
import com.hzb.erp.utils.RequestUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URL;

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
    WxPayUnifiedOrderRequest buildPayParamByOrder(Order order, String openid, String tradeType);

    /**
     * 拼接退款参数
     */
    WxPayRefundRequest buildRefundParamByOrderRefund(OrderRefund orderRefund);

    /**
    * 支付回调
    * */
    void payNotify(String xmlData) throws WxPayException;

    /**
     * 退款回调
     * */
    void refundNotify(String xmlData) throws WxPayException;
}
