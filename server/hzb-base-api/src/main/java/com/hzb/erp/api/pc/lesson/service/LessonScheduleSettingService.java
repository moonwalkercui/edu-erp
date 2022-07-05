package com.hzb.erp.api.pc.lesson.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.lesson.entity.LessonScheduleSetting;
import com.hzb.erp.api.pc.lesson.pojo.LessonWeekdayTimeBO;

import java.util.List;

/**
 * <p>
 * 课次编排上课时间 服务类
 * </p>
 *
 * @author Ryan
 */
public interface LessonScheduleSettingService extends IService<LessonScheduleSetting> {

    /**
     * 获取列表
     */
    List<LessonScheduleSetting> getListByScheduleId(Long scheduleId);

    /**
     * 删除
     */
    Boolean removeByScheduleId(Long scheduleId);

    /**
     * 获取上课时间详细列表(展开星期字段,并排序后的)
     */
    List<LessonWeekdayTimeBO> getLessonWeekdayTimeList(List<LessonScheduleSetting> list);

    /**
     * 上课时间描述文字
     */
    List<String> makeLessonTimeInfo(Long scheduleId);
}
