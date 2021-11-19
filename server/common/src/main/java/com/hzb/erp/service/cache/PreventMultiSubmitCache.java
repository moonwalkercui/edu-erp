package com.hzb.erp.service.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * <p> 重复提交缓存 </p>
 *
 * @author Ryan 541720500@qq.com
 */
public class PreventMultiSubmitCache {

    private static Cache<String, String> caches = CacheBuilder.newBuilder()
            /* 设置缓存容器的初始容量大小为10 */
//            .initialCapacity(100)
            /* 设置缓存容器的最大容量大小为100 */
            .maximumSize(1000)
            /* 设置过期时间为秒内不能冲突提交 */
            .expireAfterWrite(1, TimeUnit.SECONDS)
            .build();

    /**
    * 是否锁定中 false未锁定
    * */
    public static Boolean isLocked(String lockName) {
        String res = caches.getIfPresent(lockName);
        if (StringUtils.isBlank(res)) {
            caches.put(lockName, "locked");
            return false;
        } else {
            return true;
        }
    }

}
