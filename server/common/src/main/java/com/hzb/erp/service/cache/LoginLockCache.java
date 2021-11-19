package com.hzb.erp.service.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * <p> 登录错误锁 </p>
 *
 * @author Ryan 541720500@qq.com
 */
public class LoginLockCache {

    private static final String KEY = "login_fail_lock_";
    /**
     * 输入错误次数
     */
    private static final int FAIL_TIMES = 5;
    /**
     * 多少秒内输入错误次数会累计 缓存过期时间
     */
    private static final int EXPIRED_SEC = 600;

    private static Cache<String, Integer> caches = CacheBuilder.newBuilder()
            .maximumSize(5000)
            .expireAfterWrite(EXPIRED_SEC, TimeUnit.SECONDS)
            .build();

    /**
    * 登录错误处理
    * */
    public static void fail(String username) {
        Integer present = caches.getIfPresent(makeKey(username));
        if (present == null) {
            caches.put(makeKey(username), 1);
        } else {
            if (present >= FAIL_TIMES) {
                throw new RuntimeException("登录次数过多，请稍后再试");
            }
            caches.put(makeKey(username), present + 1);
        }
    }

    /**
     * 手动清除登录错误限制
     * */
    public static void clear(String username) {
        caches.invalidate(makeKey(username));
    }

    private static String makeKey(String mobile) {
        return KEY + "_" + mobile;
    }
}
