package com.hzb.erp.service.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * <p> 验证码缓存 </p>
 */
public class CaptchaCache {

    private static final String KEY = "captcha_key";

    private static Cache<String, String> caches = CacheBuilder.newBuilder()
            .maximumSize(5000)
            .expireAfterWrite(90, TimeUnit.SECONDS)
            .build();

    public static void put(String value, String uuid) {
        caches.put(KEY + uuid, value);
    }

    public static String get(String uuid) {
        String code = caches.getIfPresent(KEY + uuid);
        caches.invalidate(KEY + uuid);
        return code;
    }

    public static boolean valid(String inputCode, String uuid) {
        if(StringUtils.isBlank(inputCode)) {
            return false;
        }
        String stored = get(uuid);
        return inputCode.equals(stored);
    }
}
