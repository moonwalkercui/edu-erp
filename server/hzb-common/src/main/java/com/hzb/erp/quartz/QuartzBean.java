package com.hzb.erp.quartz;

import lombok.Data;

/**
 * <p> quartz实体类 </p>
 *
 * @author Ryan 541720500@qq.com
 */
@Data
public class QuartzBean {

    /** 任务名称 */
    private String jobName;

    /** 分组 */
    private String groupName;

    /** 任务执行类 */
    private String jobClass;

    /** 任务状态 启动还是暂停*/
    private Boolean status;

    /** 任务运行时间表达式 */
    private String cronExpression;
}
