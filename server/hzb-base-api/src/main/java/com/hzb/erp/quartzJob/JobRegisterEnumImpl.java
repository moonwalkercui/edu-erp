package com.hzb.erp.quartzJob;

import com.hzb.erp.quartz.JobRegisterEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * job注册枚举类
 * <p> 需要注册新任务的增加枚举类即可 </p>
 *
 * @author Ryan 541720500@qq.com
 */
@Getter
@AllArgsConstructor
public enum JobRegisterEnumImpl implements JobRegisterEnum {

    /**
     * job枚举类包列表
     */
    LESSON_ENDING_JOB("com.hzb.erp.quartzJob.jobs.LessonEndingJob", "0 0/5 * * * ?"),
    LESSON_REMIND_JOB("com.hzb.erp.quartzJob.jobs.LessonRemindJob", "0 0 19 * * ?"),
    LESSON_COUNT_WARNING_JOB("com.hzb.erp.quartzJob.jobs.LessonCountWarningJob", "0 0 7 * * ?");

    /**
     * 类包路径
     */
    private String jobClass;

    /**
     * 默认执行时间表达式
     */
    private String defaultCronExp;

}