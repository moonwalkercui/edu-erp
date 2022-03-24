package com.hzb.erp.quartz.demo;//package com.hzb.erp.quartz.demo;
//
//import com.hzb.erp.quartz.QuartzBean;
//import com.hzb.erp.quartz.QuartzUtil;
//import com.hzb.erp.quartz.entity.QuartzJob;
//import com.hzb.erp.quartz.mapper.QuartzCronMapper;
//import org.quartz.Scheduler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//
///**
// * <p> </p>
// *
// * @author Ryan 541720500@qq.com
// */
////@Controller
////@RequestMapping("/quartz/")
//public class QuartzController {
//    //注入任务调度
//    @Autowired
//    private Scheduler scheduler;
//
//    @Autowired
//    private QuartzCronMapper quartzCronMapper;
//
//    @RequestMapping("/list")
//    @ResponseBody
//    public String list() {
//        List<QuartzJob> list = quartzCronMapper.selectList(null);
//        System.out.println(list);
//        for(QuartzJob item : list) {
//            QuartzUtil.startNewJob(scheduler, item);
//        }
//        return "OK";
//    }
//
//    @RequestMapping("/reset")
//    @ResponseBody
//    public String reset() {
//        List<QuartzJob> list = quartzCronMapper.selectList(null);
//        System.out.println(list);
//        for(QuartzJob item : list) {
//            QuartzUtil.resetJob(scheduler, item);
//        }
//        return "OK";
//    }
//
//    @RequestMapping("/createJob")
//    @ResponseBody
//    public String createJob(QuartzBean quartzBean) {
//        try {
//            //进行测试所以写死
//            quartzBean.setJobClass("com.example.demo.quartz.jobs.LessonEndingJob");
//            quartzBean.setJobName("test1");
//            quartzBean.setCronExpression("*/1 * * * * ?");
//            QuartzUtil.startNewJob(scheduler, quartzBean);
//        } catch (Exception e) {
//            return "创建失败";
//        }
//        return "创建成功";
//    }
//
//    @RequestMapping("/pauseJob")
//    @ResponseBody
//    public String pauseJob() {
//        try {
//            QuartzUtil.pauseJob(scheduler, "test1");
//        } catch (Exception e) {
//            return "暂停失败";
//        }
//        return "暂停成功";
//    }
//
//    @RequestMapping("/runOnce")
//    @ResponseBody
//    public String runOnce() {
//        try {
//            QuartzUtil.runOnce(scheduler, "test1");
//        } catch (Exception e) {
//            return "运行一次失败";
//        }
//        return "运行一次成功";
//    }
//
//    @RequestMapping("/resume")
//    @ResponseBody
//    public String resume() {
//        try {
//
//            QuartzUtil.resumeJob(scheduler, "test1");
//        } catch (Exception e) {
//            return "启动失败";
//        }
//        return "启动成功";
//    }
//
//    @RequestMapping("/update")
//    @ResponseBody
//    public String update(QuartzBean quartzBean) {
//        try {
//            //进行测试所以写死
//            quartzBean.setJobName("test1");
//            quartzBean.setCronExpression("0/3 * * * * ?");
//            QuartzUtil.updateJob(scheduler, quartzBean);
//        } catch (Exception e) {
//            return "启动失败";
//        }
//        return "启动成功";
//    }
//}