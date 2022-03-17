package com.hzb.erp.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * quartz工具，主要负责新建、启动、重启、删除等操作
 * @author Ryan
 */
@Slf4j
public class QuartzUtil {

    /**
     * 重启任务
     * @param scheduler   调度器
     * @param quartzBean  定时任务信息类
     */
    public static void rebootJob(Scheduler scheduler, QuartzBean quartzBean){
        deleteJob(scheduler, quartzBean.getJobName(), quartzBean.getGroupName());
        startNewJob(scheduler, quartzBean);
    }

    /**
     * 创建定时任务 定时任务创建之后默认启动状态
     * @param scheduler   调度器
     * @param quartzBean  定时任务信息类
     */
    public static void startNewJob(Scheduler scheduler, QuartzBean quartzBean){
        try {
            //获取到定时任务的执行类  必须是类的绝对路径名称
            //定时任务类需要是job类的具体实现 QuartzJobBean是job的抽象类。
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(quartzBean.getJobClass());
            // 构建定时任务信息
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(quartzBean.getJobName(), quartzBean.getGroupName()).build();
            // 设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
            // 构建触发器trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(quartzBean.getJobName(), quartzBean.getGroupName()).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (ClassNotFoundException e) {
            log.error("定时任务类路径出错：请输入类的绝对路径");
        } catch (SchedulerException e) {
            log.error("创建定时任务出错："+e.getMessage());
        }
    }

    /**
     * 根据任务名称暂停定时任务
     * @param scheduler  调度器
     * @param jobName    定时任务名称
     */
    public static void pauseJob(Scheduler scheduler, String jobName){
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error("暂停定时任务出错："+e.getMessage());
        }
    }

    /**
     * 根据任务名称恢复定时任务
     * @param scheduler  调度器
     * @param jobName    定时任务名称
     */
    public static void resumeJob(Scheduler scheduler, String jobName) {
        resumeJob(scheduler, jobName, null);
    }

    public static void resumeJob(Scheduler scheduler, String jobName, String groupName){
        JobKey jobKey = JobKey.jobKey(jobName, groupName);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error("启动定时任务出错："+e.getMessage());
        }
    }

    /**
     * 根据任务名称立即运行一次定时任务
     * @param scheduler     调度器
     * @param jobName       定时任务名称
     */
    public static void runOnce(Scheduler scheduler, String jobName){
        runOnce(scheduler, jobName, null);
    }
    /**
     * 根据任务名称立即运行一次定时任务 带分组
     * @param scheduler     调度器
     * @param jobName       定时任务名称
     */
    public static void runOnce(Scheduler scheduler, String jobName, String groupName){
        JobKey jobKey = JobKey.jobKey(jobName, groupName);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            log.error("运行定时任务出错："+e.getMessage());
        }
    }

    /**
     * 更新定时任务
     * @param scheduler   调度器
     * @param quartzBean  定时任务信息类
     */
    public static void updateJob(Scheduler scheduler, QuartzBean quartzBean)  {
        try {
            //获取到对应任务的触发器
            TriggerKey triggerKey = TriggerKey.triggerKey(quartzBean.getJobName(), quartzBean.getGroupName());
            //设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
            //重新构建任务的触发器trigger
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //重置对应的job
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.error("更新定时任务出错："+e.getMessage());
        }
    }

    /**
     * 根据定时任务名称从调度器当中删除定时任务 不带分组
     * @param scheduler 调度器
     * @param jobName   定时任务名称
     */
    public static void deleteJob(Scheduler scheduler, String jobName) {
        deleteJob(scheduler, jobName, null);
    }

    /**
     * 根据定时任务名称从调度器当中删除定时任务
     * @param scheduler 调度器
     * @param jobName   定时任务名称
     */
    public static void deleteJob(Scheduler scheduler, String jobName, String groupName) {
        JobKey jobKey = JobKey.jobKey(jobName, groupName);
        try {
            if(scheduler.checkExists(jobKey)) {
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            log.error("删除定时任务出错："+e.getMessage());
        }
    }

}