package com.hzb.erp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p> 防止重复请求 </p>
 *
 * @author Ryan 541720500@qq.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreventMultiSubmit {

    /**
     * 设置请求锁定时间
     *
     * @return
     */
    boolean enable() default true;
}
