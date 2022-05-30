package com.hzb.erp.adminCenter.quartzJob.jobs;

import com.hzb.erp.common.service.LessonService;
import lombok.SneakyThrows;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * <p> 课次数预警提醒 </p>
 *
 * @author Ryan 541720500@qq.com
 */
public class LessonCountWarningJob extends QuartzJobBean {

    @Autowired
    private LessonService lessonService;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        lessonService.lessonLessWarning();

    }
}
