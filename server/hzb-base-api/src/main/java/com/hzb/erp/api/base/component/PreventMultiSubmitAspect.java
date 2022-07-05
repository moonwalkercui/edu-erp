package com.hzb.erp.api.base.component;

import com.hzb.erp.api.base.annotation.PreventMultiSubmit;
import com.hzb.erp.api.base.service.UserAuthService;
import com.hzb.erp.service.cache.PreventMultiSubmitCache;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> 重复请求 </p>
 *
 * @author Ryan 541720500@qq.com
 */
@Aspect
@Component
@Slf4j
public class PreventMultiSubmitAspect {

    @Pointcut("@annotation(preventMultiSubmit)")
    public void pointCut(PreventMultiSubmit preventMultiSubmit) {
    }

    /**
     * 通过缓存防止重复请求切面
     */
    @Around("pointCut(preventMultiSubmit)")
    public Object around(ProceedingJoinPoint joinPoint, PreventMultiSubmit preventMultiSubmit) throws Throwable {

        boolean enabled = preventMultiSubmit.enable();
        if (!enabled) {
            return joinPoint.proceed();
        }
        HttpServletRequest request = RequestUtil.getRequest();
        Long userId = UserAuthService.getCurrentUserId();
        if (userId == null) {
            return joinPoint.proceed();
        }
        String path = request.getServletPath();
        String key = userId + "_" + path;

        boolean isLock = PreventMultiSubmitCache.isLocked(key);
        if (!isLock) {
            return joinPoint.proceed();
        } else {
            log.error("出现重复请求:, key = [{}]", key);
            return JsonResponseUtil.error("请求过于频繁");
        }
    }

}
