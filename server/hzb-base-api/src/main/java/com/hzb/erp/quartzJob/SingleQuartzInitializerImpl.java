package com.hzb.erp.quartzJob;

import com.hzb.erp.quartz.QuartzInitializer;
import com.hzb.erp.quartz.QuartzUtil;
import com.hzb.erp.quartz.entity.QuartzJob;
import com.hzb.erp.quartz.mapper.QuartzJobMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <p> quartz初始化服务 </p>
 *
 * @author Ryan 541720500@qq.com
 */
@ConditionalOnExpression("${system.isSaas:false}==false")
@Service
@Slf4j
public class SingleQuartzInitializerImpl implements QuartzInitializer {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private QuartzJobMapper quartzJobMapper;

    /**
    * 自动启动
    * */
    @PostConstruct
    public void init() {
        log.info("============= Quartz：单服务模式下启动定时任务==========");
        List<QuartzJob> list = quartzJobMapper.getList();
        initJobDb(list);
        startAll(list);
        log.info("============= Quartz：启动定时任务完毕================");
    }

    /**
    * 初始化的时候把计划任务插入到数据库里。
    * */
    @Override
    public void initJobDb(List<QuartzJob> list) {
        for (JobRegisterEnumImpl jobEnum : JobRegisterEnumImpl.values()) {
            existOrRegisterJob(jobEnum, list);
        }
    }

    public void existOrRegisterJob(JobRegisterEnumImpl jobEnum, List<QuartzJob> list) {
        boolean exist = false;

        for(QuartzJob job : list) {
            if(jobEnum.name().equals(job.getJobName())) {
                exist = true;
                break;
            }
        }
        // 如果数据库不存在新增一个job
        if(!exist) {
            QuartzJob res = new QuartzJob();
            res.setJobName(jobEnum.name());
            res.setGroupName(getJobGroupName());
            res.setJobClass(jobEnum.getJobClass());
            res.setStatus(true);
            res.setCronExpression(jobEnum.getDefaultCronExp());
            quartzJobMapper.insert(res);
        }
    }

    @Override
    public void startAll(List<QuartzJob> list) {
        for (QuartzJob item : list) {
            item.setGroupName(getJobGroupName());
            QuartzUtil.rebootJob(scheduler, item);
        }
    }

    /**
    * 单服务模式下返回null
    * */
    @Override
    public String getJobGroupName() {
        return null;
    }

}
