package com.hzb.erp.quartz.demo;//package com.hzb.erp.quartz.jobs;
//
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.springframework.scheduling.quartz.QuartzJobBean;
//
//import java.util.Date;
//
///**
// * <p> DEMO </p>
// *
// * @author Ryan 541720500@qq.com
// */
//public class DemoJob extends QuartzJobBean {
//
//    /**
//     * 默认时间 5 分钟一次
//     */
//    public static final String DEFAULT_CRON = "0 0/5 * * * ?";
//
//    @Override
//    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//
//        //        //获取JobDetail中传递的参数
////        String userName = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("userName");
////        String blogRemark = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("blogRemark");
////
////        //获取当前时间
////        LocalDateTime ldt = LocalDateTime.now();
////        String format = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
////        //打印信息
////        System.out.println("用户名称：" + userName);
////        System.out.println("博客信息：" + blogRemark);
////        System.out.println("当前时间：" + format);
//
////        Account account = new Account();
////        account.setId(1);
////        account = service.findByAccount(account);
////        System.out.println(account.toString());
//        System.out.println("动态的定时任务执行时间：" + new Date());
//    }
//}
