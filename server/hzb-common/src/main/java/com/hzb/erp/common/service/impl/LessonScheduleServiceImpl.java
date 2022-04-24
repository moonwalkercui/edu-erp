package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.*;
import com.hzb.erp.common.enums.LessonStateEnum;
import com.hzb.erp.common.enums.SignStateEnum;
import com.hzb.erp.common.enums.TeacherTypeEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.LessonScheduleMapper;
import com.hzb.erp.common.pojo.bo.LessonDatetimeAndTeacherBO;
import com.hzb.erp.common.pojo.bo.LessonDatetimeBO;
import com.hzb.erp.common.pojo.bo.LessonWeekdayTimeBO;
import com.hzb.erp.common.pojo.dto.LessonScheduleParamDTO;
import com.hzb.erp.common.pojo.dto.lessonSchedule.ScheduleSaveDTO;
import com.hzb.erp.common.pojo.dto.lessonSchedule.ScheduleSettingDTO;
import com.hzb.erp.common.pojo.vo.LessonScheduleVO;
import com.hzb.erp.common.service.*;
import com.hzb.erp.utils.DateTool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课次编排 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class LessonScheduleServiceImpl extends ServiceImpl<LessonScheduleMapper, LessonSchedule> implements LessonScheduleService {

    @Autowired
    private ClazzService clazzService;
    @Autowired
    private CourseService courseService;

    @Autowired
    @Lazy
    private LessonService lessonService;
    @Autowired
    private ClassStudentService classStudentService;

    @Autowired
    private LessonScheduleSettingService lessonScheduleSettingService;

    @Autowired
    private LessonTeacherService lessonTeacherService;

    @Autowired
    private LessonStudentService lessonStudentService;

    @Autowired
    private HolidayService holidayService;

    @Override
    @Transactional
    public LessonSchedule saveOrUpdate(ScheduleSaveDTO dto) {

        lessonTeacherService.validateTeacherIds(dto.getTeacherIds(), dto.getAssistantIds());

        boolean isUpdate = dto.getId() != null;

        LessonSchedule ls = new LessonSchedule();

        Clazz clazz = clazzService.getById(dto.getClassId());

        ls.setId(dto.getId());
        ls.setClassId(dto.getClassId());
        ls.setCourseId(clazz.getCourseId());
        ls.setTeacherIds(StringUtils.join(dto.getTeacherIds(), ","));
        ls.setAssistantIds(StringUtils.join(dto.getAssistantIds(), ","));
        ls.setStartDate(dto.getStartDate());
        ls.setEndDate(dto.getEndDate());
        ls.setDecLessonCount(dto.getDecLessonCount());

        if (isUpdate) {
            // 更新是保留原状态
            LessonSchedule oldData = this.getById(dto.getId());
            ls.setState(oldData.getState());

        } else {

            ls.setState(false);
        }

        Boolean excludeHoliday;
        if (dto.getExcludeHoliday() == null) {
            excludeHoliday = false;
        } else {
            excludeHoliday = dto.getExcludeHoliday();
        }

        ls.setExcludeHoliday(excludeHoliday);

        // 上课时间待插入列表
        List<LessonScheduleSetting> settings = new ArrayList<>();
        for (ScheduleSettingDTO s : dto.getSetting()) {
            LessonScheduleSetting item = new LessonScheduleSetting();

            Collections.sort(s.getWeeks());
            item.setWeeks(StringUtils.join(s.getWeeks(), ","));
            item.setStartTime(s.getStartTime());
            item.setEndTime(s.getEndTime());
            item.setRoomId(s.getRoomId());
            settings.add(item);
        }

        // 计算排课次数
        List<LessonWeekdayTimeBO> tdList = lessonScheduleSettingService.getLessonWeekdayTimeList(settings);
        List<LessonDatetimeBO> lessonDatetimeList = calLessonDateByDateRange(dto.getStartDate(), dto.getEndDate(), tdList, excludeHoliday, dto.getTimes());
        ls.setTimes(lessonDatetimeList.size());

        // 插入
        this.saveOrUpdate(ls);

        Long scheduleId = ls.getId();

        // 更新上课时间待插入列表的所属编排计划id
        for (LessonScheduleSetting item : settings) {
            item.setScheduleId(scheduleId);
        }

        // 删除旧的上课时间
        lessonScheduleSettingService.removeByScheduleId(scheduleId);
        // 保存上课时间
        lessonScheduleSettingService.saveBatch(settings);

        return ls;
    }

    @Override
    public IPage<LessonScheduleVO> getList(LessonScheduleParamDTO param) {
        IPage<LessonScheduleVO> list = this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);

        for (LessonScheduleVO vo : list.getRecords()) {
            vo.setLessonTimesInfo(lessonScheduleSettingService.makeLessonTimeInfo(vo.getId()));
        }

        return list;
    }

    @Override
    public List<LessonDatetimeBO> getLessonDatetime(LessonSchedule schedule) {
        List<LessonScheduleSetting> settingList = lessonScheduleSettingService.getListByScheduleId(schedule.getId());
        List<LessonWeekdayTimeBO> wdList = lessonScheduleSettingService.getLessonWeekdayTimeList(settingList);
        return calLessonDateByDateRange(schedule.getStartDate(), schedule.getEndDate(), wdList, schedule.getExcludeHoliday(), schedule.getTimes());
    }

    @Override
    public List<LessonDatetimeAndTeacherBO> getLessonDatetimeAndTeacher(LessonSchedule schedule) {
        List<LessonDatetimeBO> boList = getLessonDatetime(schedule);

        // 保存老师分表
        List<String> teacherIds = new ArrayList<>();
        if (schedule.getTeacherIds() != null) {
            teacherIds = Arrays.asList(schedule.getTeacherIds().split(","));
        }

        List<LessonDatetimeAndTeacherBO> list = new ArrayList<>();
        for (LessonDatetimeBO bo : boList) {
            LessonDatetimeAndTeacherBO teacherBO = new LessonDatetimeAndTeacherBO();
            BeanUtils.copyProperties(bo, teacherBO);
            for (String teacherId : teacherIds) {
                teacherBO.setTeacherId(Long.valueOf(teacherId));
            }
            list.add(teacherBO);
        }
        return list;
    }


    /**
     * 计算周期内的日期列表 可排除节日,包含星期几
     **/
    @Override
    public List<LessonDatetimeBO> calLessonDateByDateRange(LocalDate startDate,
                                                           LocalDate endDate,
                                                           List<LessonWeekdayTimeBO> lessonWeekdayTimeList,
                                                           Boolean excludeHoliday,
                                                           Integer limitDays) {

        List<LocalDate> dates = new ArrayList<>();

        if (lessonWeekdayTimeList.size() == 0) {
            return new ArrayList<>();
        }

        List<Integer> wd = new ArrayList<>();
        for (LessonWeekdayTimeBO day : lessonWeekdayTimeList) {
            wd.add(day.getWeekday());
        }

        List<LocalDate> everyDates = DateTool.findEveryDates(startDate, endDate);

        List<Holiday> holidays = holidayService.list();

        int flag = 0;
        for (LocalDate date : everyDates) {
            // 如果星期里包含则追加
            if (wd.contains(date.getDayOfWeek().getValue())) {
                // 如果不排除假日 直接追加;
                if (!excludeHoliday) {
                    dates.add(date);
                    flag++;
                    if (limitDays != null && flag >= limitDays) {
                        break;
                    }
                } else {
                    // 如果开启了排除假日 且 不是节日则追加;
//                    if (!DateTool.isHoliday(DateTool.localDateToDate(date))) {
                    if (!isHoliday(date, holidays)) {
                        dates.add(date);
                        flag++;
                        if (limitDays != null && flag >= limitDays) {
                            break;
                        }
                    }
                }
            }
        }

        return bindWeekdayToLessonDatetimeList(lessonWeekdayTimeList, dates);
    }

    private Boolean isHoliday(LocalDate date, List<Holiday> holidays) {
        if (date == null || holidays == null || holidays.size() == 0) {
            return false;
        }
        for (Holiday day : holidays) {
            if (day.getDate().isEqual(date)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public Integer generateLesson(List<Long> ids) {
        List<LessonSchedule> list = this.listByIds(ids);
        Integer count = 0;
        for (LessonSchedule schedule : list) {
            if (schedule.getState()) {
                throw new BizException("已生成课表的计划无法再次生成");
            }
        }
        for (LessonSchedule schedule : list) {
            count += doGenerate(schedule);
        }
        return count;
    }

    @Override
    public Integer checkConflict(List<Long> ids) {

        List<LessonSchedule> list = this.listByIds(ids);

        for (LessonSchedule schedule : list) {
            if (schedule.getState()) {
                throw new BizException("已生成课表的计划不需检查冲突");
            }
        }
        Integer count = 0;
        for (Long id : ids) {
            LessonSchedule schedule = this.getById(id);
            count += checkConflictByDO(schedule);
        }
        return count;
    }

    /**
     * 检查一个计划的冲突 返回发生冲突的lesson id列表
     *
     * @return Integer 冲突的数量
     */
    private Integer checkConflictByDO(LessonSchedule schedule) {

        List<LessonDatetimeAndTeacherBO> teacherBO = getLessonDatetimeAndTeacher(schedule);

        List<Long> res = new ArrayList<>();
        for (LessonDatetimeAndTeacherBO bo : teacherBO) {
            List<Long> checked = this.baseMapper.checkConflict(bo);
            if (checked != null) {
                res.addAll(checked);
            }
        }

        // 去重
        List<Long> conflictIds = res.stream().distinct().collect(Collectors.toList());
        schedule.setConflictIds(StringUtils.join(conflictIds, ","));
        this.updateById(schedule);

        return conflictIds.size();
    }

    /**
     * 生成课表
     */
    private Integer doGenerate(LessonSchedule schedule) {

        List<LessonDatetimeBO> lessonList = getLessonDatetime(schedule);
        if (lessonList.size() == 0) {
            return 0;
        }

        List<String> teacherIds = new ArrayList<>();
        if (StringUtils.isNotBlank(schedule.getTeacherIds())) {
            teacherIds = Arrays.asList(schedule.getTeacherIds().split(","));
        }

        List<String> assistantIds = new ArrayList<>();
        if (StringUtils.isNotBlank(schedule.getAssistantIds())) {
            assistantIds = Arrays.asList(schedule.getAssistantIds().split(","));
        }

        Clazz clazz = clazzService.getById(schedule.getClassId());
        Course course = courseService.getById(clazz.getCourseId());

        List<Lesson> lessons = new ArrayList<>();
        Integer sn = 0;
        for (LessonDatetimeBO bo : lessonList) {
            sn++;
            Lesson newLesson = new Lesson();
            newLesson.setTitle(course.getName() + " 第" + sn + "次课");
            newLesson.setSn(sn);
            newLesson.setScheduleId(schedule.getId());
            newLesson.setCourseId(clazz.getCourseId());
            newLesson.setDate(bo.getDate());
            newLesson.setStartTime(bo.getStartTime());
            newLesson.setEndTime(bo.getEndTime());
            newLesson.setClassId(schedule.getClassId());
            newLesson.setRoomId(bo.getRoomId());
            newLesson.setDecCount(schedule.getDecLessonCount());
            newLesson.setState(LessonStateEnum.UNDERWAY);
            newLesson.setTeacherId(Long.valueOf(teacherIds.get(0)));
            newLesson.setBookable(course.getBookable() != null && course.getBookable());
            lessons.add(newLesson);
        }

        if (lessonService.saveBatch(lessons)) {

            schedule.setState(true);
            this.updateById(schedule);


            // 保存老师分表
            List<LessonTeacher> teacherList = new ArrayList<>();
            for (Lesson l : lessons) {
                for (String teacherId : teacherIds) {
                    LessonTeacher lt = new LessonTeacher();
                    lt.setLessonId(l.getId());
                    lt.setTeacherId(Long.valueOf(teacherId));
                    lt.setTypeNum(TeacherTypeEnum.TEACHER.getCode());
                    teacherList.add(lt);
                }
                if (assistantIds.size() > 0) {
                    for (String assistantId : assistantIds) {
                        LessonTeacher lt = new LessonTeacher();
                        lt.setLessonId(l.getId());
                        lt.setTeacherId(Long.valueOf(assistantId));
                        lt.setTypeNum(TeacherTypeEnum.ASSISTANT.getCode());
                        teacherList.add(lt);
                    }
                }
            }
            lessonTeacherService.saveBatch(teacherList);

            // 生成学生记录
            for (Lesson l : lessons) {
                List<Student> studentList = classStudentService.getStudentsByClassId(l.getClassId());
                if (studentList != null && studentList.size() > 0) {
                    List<LessonStudent> lsList = new ArrayList<>();
                    for (Student student : studentList) {
                        LessonStudent newLs = new LessonStudent();
                        newLs.setLessonId(l.getId());
                        newLs.setClassId(l.getClassId());
                        newLs.setStudentId(student.getId());
                        newLs.setSignState(SignStateEnum.NONE);
                        newLs.setConsumeCourseId(clazz.getCourseId());
                        newLs.setCounselor(student.getCounselor());
                        lsList.add(newLs);
                    }
                    lessonStudentService.saveBatch(lsList);
                }
            }

            return sn;
        }
        return 0;
    }


    /**
     * 给每个日期绑定上课时间
     */
    private List<LessonDatetimeBO> bindWeekdayToLessonDatetimeList(List<LessonWeekdayTimeBO> lessonWeekdayTimeList, List<LocalDate> dateList) {

        List<LessonDatetimeBO> res = new ArrayList<>();
        for (LocalDate date : dateList) {
            for (LessonWeekdayTimeBO wd : lessonWeekdayTimeList) {
                if (date.getDayOfWeek().getValue() == wd.getWeekday()) {
                    LessonDatetimeBO dt = new LessonDatetimeBO();
                    BeanUtils.copyProperties(wd, dt);
                    dt.setDate(date);
                    res.add(dt);
                }
            }
        }
        return res;
    }


}
