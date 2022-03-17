package com.hzb.erp.quartz;

import lombok.Getter;

/**
 * <p> </p>
 *
 * @author Ryan 541720500@qq.com
 */
public interface JobRegisterEnum {
    /**
     * 类包路径
     */
    String getJobClass();
    /**
     * 默认执行表达氏
     */
    String getDefaultCronExp();
}
