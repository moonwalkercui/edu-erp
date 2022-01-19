package com.hzb.erp.annotation;

import java.lang.annotation.*;

/**
 * 不验证登录注解，Controller使用此注解，过滤器将不拦截
 *
 * @author Ryan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface LoginNotRequired {
    boolean value() default true;
}
