package com.hzb.erp.quartz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hzb.erp.quartz.QuartzBean;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * quarz定时任务实体类
 */
@Data
@TableName("quartz_job")
public class QuartzJob extends QuartzBean {

    /** 任务id */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /** 任务名称 */
    @NotNull
    private String jobName;

    /** 分组 */
    private String groupName;

    /** 任务执行类 */
    @NotNull
    private String jobClass;

    /** 任务状态 启动还是暂停*/
    private Boolean status;

    /** 任务运行时间表达式 */
    private String cronExpression;

}