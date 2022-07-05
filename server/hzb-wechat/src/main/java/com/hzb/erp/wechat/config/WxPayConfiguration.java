package com.hzb.erp.wechat.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.hzb.erp.wechat.service.WechatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付bean
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
@AllArgsConstructor
@Slf4j
public class WxPayConfiguration {
  private final WechatService wechatService;

  @Bean
  @ConditionalOnMissingBean
  public WxPayService wxPayService() {
    WxPayService service = new WxPayServiceImpl();
    WxPayConfig payConfig = wechatService.getPayConfig();

    log.info("微信支付服务参数：");
    log.info(String.valueOf(payConfig));

    if(payConfig == null) {
      // throw new RuntimeException("缺少微信支付配置,请检查");
      log.warn("缺少微信公众号配置,请检查");
      return service;
    }
    // 可以指定是否使用沙箱环境
    payConfig.setUseSandboxEnv(false);
    service.setConfig(payConfig);
    return service;
  }

}
