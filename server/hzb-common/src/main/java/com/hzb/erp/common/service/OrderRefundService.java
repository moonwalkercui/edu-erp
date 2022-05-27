package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.OrderRefund;
import com.hzb.erp.common.pojo.dto.IdsAndContentDTO;
import com.hzb.erp.common.pojo.dto.OrderRefundDTO;
import com.hzb.erp.common.pojo.dto.OrderRefundParamDTO;
import com.hzb.erp.common.pojo.vo.OrderRefundVo;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.util.List;

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
