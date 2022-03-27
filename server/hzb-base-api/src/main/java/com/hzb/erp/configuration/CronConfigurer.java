package com.hzb.erp.configuration;

import com.hzb.erp.common.configuration.SystemConfig;
import com.hzb.erp.common.mapper.CommonMapper;
import com.hzb.erp.common.service.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * 系统定时任务配置 生产下可以注释掉该类
 */
@Configuration
@EnableScheduling
@Slf4j
public class CronConfigurer {

    @Resource
    private CommonMapper commonMapper;

    @Resource
    private SystemConfig systemConfig;

    /**
     * 每隔时间恢复一次数据 生产环境禁用！！！！！！！！！！！！！！
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    void revertData() {

        if (!systemConfig.getIsDemo()) {
            return;
        }

        System.out.println("恢复数据start");
        commonMapper.revertData("contact_record");
        commonMapper.revertData("class_student");
        commonMapper.revertData("classroom");
        commonMapper.revertData("class");
        commonMapper.revertData("course");
        commonMapper.revertData("lesson");
        commonMapper.revertData("lesson_schedule");
        commonMapper.revertData("student");
        commonMapper.revertData("student_course");
        commonMapper.revertData("subject");
        commonMapper.revertStaff();
        System.out.println("恢复数据end");
    }

}
