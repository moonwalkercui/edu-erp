package com.hzb.erp.api.base.quartzJob.jobs;

import com.hzb.erp.api.pc.shop.service.OrderService;
import lombok.SneakyThrows;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * <p> 订单自动取消 </p>
 *
 * @author Ryan 541720500@qq.com
 */
public class OrderCancelJob extends QuartzJobBean {

    @Autowired
    private OrderService orderService;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        orderService.autoCloseOrder();

    }
}
