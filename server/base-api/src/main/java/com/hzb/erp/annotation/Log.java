package com.hzb.erp.annotation;

import java.lang.annotation.*;

/***
 * 日志注解
 * @author Ryan
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 描述
     */
    String description() default "";

    /**
     * 分类
     */
    String type() default "";

    /**
     * 是否员工操作的
     */
    boolean isStaff() default true;

    /**
     * 是否学生操作的
     */
    boolean isStudent() default false;

//
//    /**
//     * 方法类型 INSERT DELETE UPDATE OTHER
//     */
//    MethodType methodType() default MethodType.OTHER;
}
