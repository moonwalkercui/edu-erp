package com.hzb.erp.quartzJob.jobs;

import com.hzb.erp.common.configuration.SystemConfig;
import com.hzb.erp.common.service.LessonService;
//import com.hzb.erp.datesource.common.DBContextHolder;
//import com.hzb.erp.datesource.service.DBChangeService;
import lombok.SneakyThrows;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * <p> 上课提醒 </p>
 *
 * @author Ryan 541720500@qq.com
 */
public class LessonRemindJob extends QuartzJobBean {

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private LessonService lessonService;

//    @Resource
//    private DBChangeService dbChangeService;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        lessonService.lessonNotice();

//        if(systemConfig.getIsSaas()) {
//            String groupName = jobExecutionContext.getJobDetail().getKey().getGroup();
//            dbChangeService.changeDb(groupName);
//            lessonService.lessonNotice();
//            DBContextHolder.clearDataSource();
//        }
    }
}
