package com.hzb.erp.service.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hzb.erp.common.exception.BizException;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * <p> 登录失败 输入错误5次，登录封锁一分钟  </p>
 * 用法：
 * 1，登录方法第一行执行 LoginFailCache.valid(usernmae);
 * 2，登录出错执行一次 LoginFailCache.failHandle(usernmae);
 */
public class LoginFailLimitCache {

    private static final int LIMIT_TIMES = 5;
    private static final int LIMIT_SEC = 90;
    private static final String KEY = "login_fail_times_";

    private static Cache<String, Integer> caches = CacheBuilder.newBuilder()
            .maximumSize(5000)
            .expireAfterWrite(LIMIT_SEC, TimeUnit.SECONDS)
            .build();

    public static void failHandle(String username) {
        Integer getOne = caches.getIfPresent(KEY + username);
        if (getOne == null) {
            caches.put(KEY + username, LIMIT_TIMES);
        } else {
            caches.put(KEY + username, getOne - 1);
        }
    }

    /**
     * 有封锁就异常
     */
    public static void valid(String username) {
        if (StringUtils.isBlank(username)) {
            return;
        }
        Integer stored = caches.getIfPresent(KEY + username);
        if (stored != null && stored == 0) {
            throw new BizException("登录错误次数太多，请稍后重试");
        }
    }
}
