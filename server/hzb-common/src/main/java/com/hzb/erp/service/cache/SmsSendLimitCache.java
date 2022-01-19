package com.hzb.erp.service.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

/**
 * <p> 预防短信重复发送缓存 </p>
 *
 * @author Ryan 541720500@qq.com
 */
public class SmsSendLimitCache {

    private static final String KEY = "sms_send_limit_";
    private static final int DEFAULT_SEC = 30;

    private static Cache<String, Long> caches = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(DEFAULT_SEC, TimeUnit.SECONDS)
            .build();

    /**
     * 短信可发送的剩余秒数
     * 0表示立即可以发送，大于0表示还有多少秒可发送
     */
    public static long remainingSec(String mobile) {
        Long present = caches.getIfPresent(makeKey(mobile));
        long nowSec = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));

        if (present == null || present < nowSec) {
            caches.put(makeKey(mobile), nowSec + DEFAULT_SEC);
            return 0;
        } else {
            return present - nowSec;
        }
    }

    private static String makeKey(String mobile) {
        return KEY + "_" + mobile;
    }
}
