package com.hzb.erp.wechat.config;

import com.hzb.erp.wechat.handler.*;
import com.hzb.erp.wechat.service.WechatService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static me.chanjar.weixin.common.api.WxConsts.EventType;
import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.EventType.UNSUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;

/**
 * wechat mp configuration
 * https://github.com/Wechat-Group/WxJava
 * https://gitee.com/binary/weixin-java-mp-demo-springboot
 * https://github.com/wechat-group/WxJava/wiki
 */
@RequiredArgsConstructor
@Configuration
public class WxMpConfiguration {
    private final LogHandler logHandler;
    private final NullHandler nullHandler;
    private final LocationHandler locationHandler;
    private final MenuHandler menuHandler;
    private final MsgHandler msgHandler;
    private final UnsubscribeHandler unsubscribeHandler;
    private final SubscribeHandler subscribeHandler;
    private final ScanHandler scanHandler;

    /**
    * 定义一个微信服务bean
    * */
    @Bean
    public WxMpService wxMpService() {
        return new WxMpServiceImpl();
    }

//    /**
//     * 定义一个微信服务bean，主要是加载微信配置
//     * */
//    @Bean
//    public WxMpService wxMpService() {
//        List<WxMpProperties.MpConfig> configs = WechatService.getConfigList();
//
//        List<WxMpProperties.MpConfig> propConfigs = this.properties.getConfigs();
//        if(propConfigs != null && propConfigs.size() > 0) {
//            configs.addAll(propConfigs);
//        }
//        if (configs.size() == 0) {
//            throw new RuntimeException("未设置公众号配置");
//        }
//        WxMpService service = new WxMpServiceImpl();
//        service.setMultiConfigStorages(configs.stream().map(a -> {
//            WxMpDefaultConfigImpl configStorage;
//            if (this.properties.isUseRedis()) {
//                JedisPoolConfig poolConfig = new JedisPoolConfig();
//                JedisPool jedisPool = new JedisPool(poolConfig, redisProperty.getHost(), redisProperty.getPort(),
//                        -1, redisProperty.getPassword());
//                configStorage = new WxMpRedisConfigImpl(new JedisWxRedisOps(jedisPool), a.getAppId());
//            } else {
//                configStorage = new WxMpDefaultConfigImpl();
//            }
//            configStorage.setAppId(a.getAppId());
//            configStorage.setSecret(a.getSecret());
//            configStorage.setToken(a.getToken());
//            configStorage.setAesKey(a.getAesKey());
//            return configStorage;
//        }).collect(Collectors.toMap(WxMpDefaultConfigImpl::getAppId, a -> a, (o, n) -> o)));
//        return service;
//    }

    @Bean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(EVENT).event(EventType.CLICK).handler(this.menuHandler).end();

        // 点击菜单连接事件
        newRouter.rule().async(false).msgType(EVENT).event(EventType.VIEW).handler(this.nullHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(this.subscribeHandler).end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(EVENT).event(UNSUBSCRIBE).handler(this.unsubscribeHandler).end();

        // 上报地理位置事件
        newRouter.rule().async(false).msgType(EVENT).event(EventType.LOCATION).handler(this.locationHandler).end();

        // 接收地理位置消息
        newRouter.rule().async(false).msgType(XmlMsgType.LOCATION).handler(this.locationHandler).end();

        // 扫码事件
        newRouter.rule().async(false).msgType(EVENT).event(EventType.SCAN).handler(this.scanHandler).end();

        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }

}
