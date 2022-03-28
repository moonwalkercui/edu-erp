package com.hzb.erp.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzb.erp.common.entity.SettingOption;
import com.hzb.erp.quartz.QuartzUtil;
import com.hzb.erp.quartz.entity.QuartzJob;
import com.hzb.erp.quartz.mapper.QuartzJobMapper;
import com.hzb.erp.quartzJob.JobRegisterEnumImpl;
import com.hzb.erp.quartzJob.jobs.LessonEndingJob;
import com.hzb.erp.utils.SettingConstants;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 系统设置改变后的业务处理
 */
@Service
@Slf4j
public class SettingChangeService {

    @Autowired
    private QuartzJobMapper quartzJobMapper;

    @Autowired
    private Scheduler scheduler;

    /**
     * 某些配置项会影响其他逻辑，这是处理器
     */
    public void afterChange(SettingOption option) {

        if (SettingConstants.LESSON_REMIND_TIME.equals(option.getCode())) {
            // 上课提醒时间
            String exp = timeToCronExp(option.getValue());
            QueryWrapper<QuartzJob> qw = new QueryWrapper<>();
            qw.eq("job_name", JobRegisterEnumImpl.LESSON_REMIND_JOB.name());
            QuartzJob job = quartzJobMapper.selectOne(qw);
            job.setCronExpression(exp);
            quartzJobMapper.updateById(job);
            QuartzUtil.updateJob(scheduler, job);

        } else if (SettingConstants.LESSON_COUNT_WARNING_TIME.equals(option.getCode())) {
            // 课次数警告提醒
            String exp = timeToCronExp(option.getValue());
            QueryWrapper<QuartzJob> qw = new QueryWrapper<>();
            qw.eq("job_name", JobRegisterEnumImpl.LESSON_COUNT_WARNING_JOB.name());
            QuartzJob job = quartzJobMapper.selectOne(qw);
            job.setCronExpression(exp);
            quartzJobMapper.updateById(job);
            QuartzUtil.updateJob(scheduler, job);
        }

    }

    private String timeToCronExp(String value) {
        Date date = DateUtil.parse(value);
        int hour = DateUtil.hour(date, true);
        int minute = DateUtil.minute(date);
        return String.format("0 %d %d * * ?", minute, hour);
    }

}
