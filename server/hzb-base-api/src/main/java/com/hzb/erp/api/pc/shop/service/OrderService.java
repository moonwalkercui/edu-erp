package com.hzb.erp.api.pc.shop.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.shop.entity.Order;
import com.hzb.erp.api.pc.shop.pojo.OrderConfirmDTO;
import com.hzb.erp.api.pc.shop.pojo.OrderListParamDTO;
import com.hzb.erp.api.pc.shop.pojo.OrderVO;


/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface OrderService extends IService<Order> {

    /**
    * 创建订单 当前版本一个订单支持购买一个课程
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

    /**
    * 分页查询
    * */
    IPage<OrderVO> getList(OrderListParamDTO param);

    /**
     * 订单号生成规则
     * */
    static String makeOrderSn() {
        return IdUtil.simpleUUID();
    }

    /**
    * 自动关闭订单
    * */
    void autoCloseOrder();

    /**
     * 取消订单
     * */
    boolean cancel(Order order);

    /**
    * 检查是否可以退款
    * */
    boolean checkCanRefund(OrderVO order);

    /**
    * 订单详情
    * */
    OrderVO getInfo(Long orderId);
}
