package com.hzb.erp.wechat.config;

import com.hzb.erp.common.configuration.RedisProperty;
import com.hzb.erp.common.configuration.WxMpProperties;
import com.hzb.erp.common.enums.SettingCodeEnum;
import com.hzb.erp.common.service.SettingService;
import com.hzb.erp.utils.SettingConstants;
import com.hzb.erp.wechat.handler.*;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.redis.JedisWxRedisOps;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(value = WxMpProperties.class)
public class WxMpConfiguration {
    private final LogHandler logHandler;
    private final NullHandler nullHandler;
    private final LocationHandler locationHandler;
    private final MenuHandler menuHandler;
    private final MsgHandler msgHandler;
    private final UnsubscribeHandler unsubscribeHandler;
    private final SubscribeHandler subscribeHandler;
    private final ScanHandler scanHandler;
    private final WxMpProperties properties;

    public static List<WxMpProperties.MpConfig> configs = new ArrayList<>();

    @Autowired
    private SettingService settingService;

    @Autowired
    private RedisProperty redisProperty;

    /**
    * 从数据库加载微信默认配置，重启服务生效
    * */
    @PostConstruct
    public void initDefaultConfig() {
        WxMpProperties.MpConfig defaultConf = new WxMpProperties.MpConfig();
        // 从数据库获取：
        Map<String, Object> settings = settingService.listOptionByCode(SettingCodeEnum.WX_MP_SETTING);
        if(settings == null) {
            throw new RuntimeException("未找到微信公众号配置. Not Found Wechat Setting Named 'wx_mp_setting'.");
        }
        try {
            String wxAppId = settings.get(SettingConstants.WX_MP_APP_ID).toString();
            if(!StringUtils.isBlank(wxAppId)) {
                defaultConf.setName("default");
                defaultConf.setAppId(wxAppId);
                defaultConf.setSecret(settings.get(SettingConstants.WX_MP_SECRET).toString());
                defaultConf.setToken(settings.get(SettingConstants.WX_MP_TOKEN).toString());
                defaultConf.setAesKey(settings.get(SettingConstants.WX_MP_AES_KEY).toString());
                configs.add(defaultConf);
            }
        } catch (Exception e) {
            throw new RuntimeException("请检查数据表 setting_option 的微信配置项是否完整. Missing Wechat Setting Option In Table:setting_option");
        }
    }

    /**
    * 定义一个微信服务bean，主要是加载微信配置
    * */
    @Bean
    public WxMpService wxMpService() {
        // 从配置文件获取：
        List<WxMpProperties.MpConfig> propConfigs = this.properties.getConfigs();
        if(propConfigs != null && propConfigs.size() > 0) {
            configs.addAll(propConfigs);
        }
        if (configs.size() == 0) {
            throw new RuntimeException("未设置公众号配置");
        }
        WxMpService service = new WxMpServiceImpl();
        service.setMultiConfigStorages(configs.stream().map(a -> {
            WxMpDefaultConfigImpl configStorage;
            if (this.properties.isUseRedis()) {
                JedisPoolConfig poolConfig = new JedisPoolConfig();
                JedisPool jedisPool = new JedisPool(poolConfig, redisProperty.getHost(), redisProperty.getPort(),
                        -1, redisProperty.getPassword());
                configStorage = new WxMpRedisConfigImpl(new JedisWxRedisOps(jedisPool), a.getAppId());
            } else {
                configStorage = new WxMpDefaultConfigImpl();
            }
            configStorage.setAppId(a.getAppId());
            configStorage.setSecret(a.getSecret());
            configStorage.setToken(a.getToken());
            configStorage.setAesKey(a.getAesKey());
            return configStorage;
        }).collect(Collectors.toMap(WxMpDefaultConfigImpl::getAppId, a -> a, (o, n) -> o)));
        return service;
    }

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
