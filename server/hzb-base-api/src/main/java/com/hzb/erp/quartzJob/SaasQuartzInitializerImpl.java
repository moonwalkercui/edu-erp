//package com.hzb.erp.quartzJob;
//
//import com.hzb.erp.datesource.common.DBContextHolder;
//import com.hzb.erp.datesource.entity.DataSource;
//import com.hzb.erp.datesource.service.DBChangeService;
//import com.hzb.erp.quartz.QuartzInitializer;
//import com.hzb.erp.quartz.QuartzUtil;
//import com.hzb.erp.quartz.entity.QuartzJob;
//import com.hzb.erp.quartz.mapper.QuartzJobMapper;
//import com.hzb.erp.quartzJob.jobs.JobRegisterEnumImpl;
//import lombok.extern.slf4j.Slf4j;
//import org.quartz.Scheduler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * <p> quartz初始化服务 </p>
// *
// * @author Ryan 541720500@qq.com
// */
//@Service
//@ConditionalOnExpression("${system.isSaas:false}")
//@Slf4j
//public class SaasQuartzInitializerImpl implements QuartzInitializer {
//
//    @Autowired
//    private Scheduler scheduler;
//
//    @Autowired
//    private QuartzJobMapper quartzJobMapper;
//
//    @Resource
//    private DBChangeService dbChangeService;
//
//    /**
//     * 自动启动
//     */
//    @PostConstruct
//    public void init() throws Exception {
//        log.info("=====================Saas模式下启动定时任务================");
//        initJobDb();
//        this.startAll();
//        log.info("=====================启动定时任务完毕================");
//    }
//
//    /**
//     * 初始化的时候把计划任务插入到数据库里。
//     */
//    @Override
//    public void initJobDb() throws Exception {
//        for (JobRegisterEnumImpl jobEnum : JobRegisterEnumImpl.values()) {
//            existOrRegisterJob(jobEnum);
//        }
//    }
//
//    public void existOrRegisterJob(JobRegisterEnumImpl jobEnum) throws Exception {
//        List<DataSource> dataSources = dbChangeService.get();
//        for (DataSource dataSource : dataSources) {
//            dbChangeService.changeDb(dataSource.getDatasourceCode());
//
//            boolean exist = false;
//            List<QuartzJob> list = quartzJobMapper.getList();
//            for (QuartzJob job : list) {
//                if (jobEnum.name().equals(job.getJobName())) {
//                    exist = true;
//                    break;
//                }
//            }
//            // 如果数据库不存在新增一个job
//            if (!exist) {
//                QuartzJob res = new QuartzJob();
//                res.setJobName(jobEnum.name());
//                res.setGroupName(getJobGroupName());
//                res.setJobClass(jobEnum.getJobClass());
//                res.setStatus(true);
//                res.setCronExpression(jobEnum.getDefaultCronExp());
//                quartzJobMapper.insert(res);
//            }
//        }
//        DBContextHolder.clearDataSource();
//    }
//
//    @Override
//    public void startAll() throws Exception {
//
//        List<DataSource> dataSources = dbChangeService.get();
//        for (DataSource dataSource : dataSources) {
//            dbChangeService.changeDb(dataSource.getDatasourceCode());
//
//            List<QuartzJob> list = quartzJobMapper.getList();
//            for (QuartzJob item : list) {
//                item.setGroupName(getJobGroupName());
//                QuartzUtil.startNewJob(scheduler, item);
//            }
//
//        }
//        //默认切换到主数据源,进行整体资源的查找
//        DBContextHolder.clearDataSource();
//    }
//
//    /**
//     * 使用dabasource的code作为组名
//     */
//    @Override
//    public String getJobGroupName() {
//        return DBContextHolder.getDataSource();
//    }
//
//}
