package com.hzb.erp;

import com.hzb.erp.common.service.LessonScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


//@SpringBootTest
class ErpApplicationTests {

    @Autowired
    private LessonScheduleService lessonScheduleService;

    //    @Autowired
//    private LessonScheduleSettingService lessonScheduleSettingService;
    @Test
    void contextLoads() {
//
//        LessonSchedule schedule = lessonScheduleService.getById(51L);
//        List<LessonDatetimeBO> LessonDatetimeBOList = lessonScheduleService.getLessonDatetime(schedule);
//
//        for (LessonDatetimeBO bo: LessonDatetimeBOList) {
//            System.out.println("=============bo.getDate()");
//            System.out.println(bo.getDate());
//            System.out.println(bo.getWeekday());
//            System.out.println(bo.getStartTime());
//            System.out.println(bo.getEndTime());
//        }

//        List<LessonScheduleSetting> list = lessonScheduleSettingService.list();
//        List<LessonDatetimeBO> lessonDatetimeList = lessonScheduleSettingService.getLessonDatetimeList(list);
//        System.out.println("lessonDatetimeList");
//        lessonDatetimeList.forEach(System.out::println);


//        System.out.println(DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-02-10")));
//        System.out.println(DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-02-11")));
//        System.out.println("========================春节");
//        System.out.println(DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-02-12")));
//        System.out.println(DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-02-13")));
//        System.out.println(DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-02-14")));
//        System.out.println(DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-02-17")));
//        System.out.println(DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-02-18")));
//        System.out.println(DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-02-19")));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-02-20"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-04-03"));
//        System.out.println("========================清明");
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-04-04"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-04-05"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-04-06"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-04-30"));
//        System.out.println("========================劳动");
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-05-01"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-05-02"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-05-03"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-05-04"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-05-07"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-05-08"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-06-12"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-06-13"));
//        System.out.println("========================端午");
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-06-14"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-06-15"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-09-18"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-09-19"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-09-20"));
//        System.out.println("========================中秋");
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-09-21"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-09-22"));
//        System.out.println("========================国庆");
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-10-01"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-10-02"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-10-06"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-10-07"));
//        DateUtil.isHoliday(cn.hutool.core.date.DateUtil.parse("2021-10-08"));

    }

}
