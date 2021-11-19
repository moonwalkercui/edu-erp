package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.LessonSchedule;
import com.hzb.erp.common.pojo.bo.LessonDatetimeAndTeacherBO;
import com.hzb.erp.common.pojo.bo.LessonDatetimeBO;
import com.hzb.erp.common.pojo.bo.LessonWeekdayTimeBO;
import com.hzb.erp.common.pojo.dto.LessonScheduleParamDTO;
import com.hzb.erp.common.pojo.dto.lessonSchedule.ScheduleSaveDTO;
import com.hzb.erp.common.pojo.vo.LessonScheduleVO;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 课次编排 服务类
 * </p>
 *
 * @author Ryan
 */
public interface LessonScheduleService extends IService<LessonSchedule> {

    /**
     * 保存或更新编排计划
     */
    LessonSchedule saveOrUpdate(ScheduleSaveDTO scheduleSaveDTO);

    IPage<LessonScheduleVO> getList(LessonScheduleParamDTO param);

    /**
     * 获取上课日期和上课时间列表
     *
     * @param schedule 编排对象
     */
    List<LessonDatetimeBO> getLessonDatetime(LessonSchedule schedule);

    /**
     * 获取上课日期和上课时间以及老师的列表 比getLessonDatetime多一层teacherId的维度
     */
    List<LessonDatetimeAndTeacherBO> getLessonDatetimeAndTeacher(LessonSchedule schedule);

    /**
     * 根据时间区间,计算上课日期
     *
     * @param startDate          开始日期
     * @param endDate            结束日期
     * @param lessonDatetimeList 含星期几
     * @param excludeHoliday     是否排除节日
     * @param limitDays          最多天数
     */
    List<LessonDatetimeBO> calLessonDateByDateRange(LocalDate startDate, LocalDate endDate, List<LessonWeekdayTimeBO> lessonDatetimeList, Boolean excludeHoliday, Integer limitDays);

    Integer generateLesson(List<Long> ids);

    /**
     * 冲突检查
     */
    Integer checkConflict(List<Long> ids);
}
