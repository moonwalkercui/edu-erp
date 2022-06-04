package com.hzb.erp.service.notification.bo;

import lombok.Data;

/**
 * @author Ryan 541720500@qq.com
 * 新订单提醒
 */
@Data
public class NewOrderBO extends NoticeBO {
    // 订单号
    String orderSn;
    // 订单时间
    String orderTime;
}
