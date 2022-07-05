package com.hzb.erp.api.pc.lesson.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.pc.lesson.entity.LessonScheduleSetting;
import com.hzb.erp.api.pc.lesson.mapper.LessonScheduleSettingMapper;
import com.hzb.erp.api.pc.lesson.pojo.LessonWeekdayTimeBO;
import com.hzb.erp.api.pc.lesson.service.LessonScheduleSettingService;
import com.hzb.erp.utils.DateTool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 课次编排上课时间 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class LessonScheduleSettingServiceImpl extends ServiceImpl<LessonScheduleSettingMapper, LessonScheduleSetting> implements LessonScheduleSettingService {

    @Override
    public List<LessonScheduleSetting> getListByScheduleId(Long scheduleId) {
        QueryWrapper<LessonScheduleSetting> qw = new QueryWrapper<>();
        qw.eq("schedule_id", scheduleId).orderByDesc("id");
        return this.baseMapper.selectList(qw);
    }

    @Override
    public Boolean removeByScheduleId(Long scheduleId) {
        QueryWrapper<LessonScheduleSetting> qw = new QueryWrapper<>();
        qw.eq("schedule_id", scheduleId);
        return this.remove(qw);
    }

    @Override
    public List<LessonWeekdayTimeBO> getLessonWeekdayTimeList(List<LessonScheduleSetting> list) {

        List<LessonWeekdayTimeBO> dtList = new ArrayList<>();
        for (LessonScheduleSetting setting : list) {
            String weeksStr = setting.getWeeks();
            for (String day : weeksStr.split(",")) {
                LessonWeekdayTimeBO dt = new LessonWeekdayTimeBO();
                dt.setSettingId(setting.getScheduleId());
                dt.setWeekday(Integer.valueOf(day));
                dt.setStartTime(setting.getStartTime());
                dt.setEndTime(setting.getEndTime());
                dt.setRoomId(setting.getRoomId());
                dtList.add(dt);
            }
        }
        Collections.sort(dtList);

        return dtList;
    }

    @Override
    public List<String> makeLessonTimeInfo(Long scheduleId) {
        List<String> res = new ArrayList<>();

        List<LessonScheduleSetting> settings = getListByScheduleId(scheduleId);

        for (LessonScheduleSetting s : settings) {
            res.add(DateTool.transWeekdayText(s.getWeeks()) + " " + s.getStartTime() + "~" + s.getEndTime());
        }

        return res;
    }

}
