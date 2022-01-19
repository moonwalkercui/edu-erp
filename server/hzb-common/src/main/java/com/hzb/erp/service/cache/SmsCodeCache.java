package com.hzb.erp.service.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hzb.erp.service.enums.SmsSceneType;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * <p> 短信缓存 </p>
 */
public class SmsCodeCache {

    private static final String KEY = "sms_cache_key";

    private static Cache<String, String> caches = CacheBuilder.newBuilder()
            .maximumSize(5000)
            .expireAfterWrite(600, TimeUnit.SECONDS)
            .build();

    public static void put(String code, String mobile, SmsSceneType scene) {
        caches.put(makeKey(mobile, scene), code);
    }

    public static String get(String mobile, SmsSceneType scene) {
        String key = makeKey(mobile, scene);
        String code = caches.getIfPresent(key);
        caches.invalidate(key);
        return code;
    }

    public static boolean valid(String mobile, String code, SmsSceneType scene) {
        if (StringUtils.isBlank(code)) {
            return false;
        }
        String stored = get(mobile, scene);
        return code.equals(stored);
    }

    private static String makeKey(String mobile, SmsSceneType scene) {
        return KEY + "_" + mobile + "_" + scene.getCode();
    }
}
