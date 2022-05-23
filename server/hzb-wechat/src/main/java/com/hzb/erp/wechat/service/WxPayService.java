package com.hzb.erp.wechat.service;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.hzb.erp.common.entity.Order;
import com.hzb.erp.common.mapper.CourseMapper;
import com.hzb.erp.common.mapper.OrderItemMapper;
import com.hzb.erp.common.mapper.OrderMapper;
import com.hzb.erp.utils.IpUtil;
import com.hzb.erp.utils.RequestUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URL;

/**
 * @author Ryan 541720500@qq.com
 * gitee.com/binary/weixin-java-mp-demo-springboot
 */
@Service
@RequiredArgsConstructor
public class WxPayService {
    private static OrderItemMapper orderItemMapper;

    /**
     * 拼接支付参数
     */
    public static WxPayUnifiedOrderRequest buildParamByOrder(Order order, String openid, String tradeType) {
        String notifyUrl;
        String createIp;
        try {
            HttpServletRequest request = RequestUtil.getRequest();
            URL requestUrl = new URL(request.getRequestURL().toString());
            notifyUrl = requestUrl.getHost() + "/wx/pay/handleNotify"; // todo通知接口
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
}
