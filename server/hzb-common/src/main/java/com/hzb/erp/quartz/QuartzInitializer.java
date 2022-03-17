package com.hzb.erp.quartz;

import com.hzb.erp.quartz.entity.QuartzJob;
import com.hzb.erp.quartz.mapper.QuartzJobMapper;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * quartz初始化接口
 * <p>启动时自动执行数据库里的配置</p>
 *
 * @author Ryan
 */
public interface QuartzInitializer {

    /**
     * 初始化数据库
     */
    public void initJobDb() throws Exception;

    /**
     * 启动全部
     */
    public void startAll() throws Exception;

    /**
     * 获取Job的组名
     */
    public String getJobGroupName();

}