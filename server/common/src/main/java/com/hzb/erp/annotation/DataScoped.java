package com.hzb.erp.annotation;

import java.lang.annotation.*;

/***
 * 数据权限
 * @author Ryan
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScoped {

    /**
     * 是否支持数据权限
     */
    boolean scoped() default true;
}
