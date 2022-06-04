package com.hzb.erp.adminCenter.quartzJob;

import com.hzb.erp.adminCenter.quartzJob.jobs.OrderCancelJob;
import com.hzb.erp.quartz.JobRegisterEnum;
import com.hzb.erp.adminCenter.quartzJob.jobs.LessonCountWarningJob;
import com.hzb.erp.adminCenter.quartzJob.jobs.LessonEndingJob;
import com.hzb.erp.adminCenter.quartzJob.jobs.LessonRemindJob;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * job注册枚举类
 * <p>
 * 需要注册新任务的增加枚举类即可，
 * quartz_job表中会覆盖本类中的默认执行时间表达式，该表内容是在系统启动的时候自动维护的，用户可以通过前端更改表中的执行时间。
 * </p>
 *
 * @author Ryan 541720500@qq.com
 */
@Getter
@AllArgsConstructor
public enum JobRegisterEnumImpl implements JobRegisterEnum {

    /**
     * job枚举类包列表
     */
    // 定时结课
    LESSON_ENDING_JOB(LessonEndingJob.class.getName(), "0 */5 * * * ?"),
    // 定时上课提醒
    LESSON_REMIND_JOB(LessonRemindJob.class.getName(), "0 0 19 * * ?"),
    // 定时课时不足提醒
    LESSON_COUNT_WARNING_JOB(LessonCountWarningJob.class.getName(), "0 0 7 * * ?"),
    // 定时取消订单
    ORDER_CANCEL_JOB(OrderCancelJob.class.getName(), "0 */1 * * * ?");

    /**
     * 类包路径
     */
    private final String jobClass;

    /**
     * 默认执行时间表达式
     */
    private final String defaultCronExp;

}