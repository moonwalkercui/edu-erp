package com.hzb.erp.api.base.quartzJob.jobs;

import com.hzb.erp.api.pc.lesson.service.LessonService;
import lombok.SneakyThrows;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * <p> 结课逻辑处理 </p>
 *
 * @author Ryan 541720500@qq.com
 */
public class LessonEndingJob extends QuartzJobBean {

    @Autowired
    private LessonService lessonService;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        lessonService.closeLesson();

    }
}
