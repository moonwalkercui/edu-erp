package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.*;
import com.hzb.erp.common.enums.LessonStateEnum;
import com.hzb.erp.common.enums.SignStateEnum;
import com.hzb.erp.common.enums.SignTypeEnum;
import com.hzb.erp.common.enums.TeacherTypeEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.LessonMapper;
import com.hzb.erp.common.mapper.LessonScheduleMapper;
import com.hzb.erp.common.mapper.LessonStudentMapper;
import com.hzb.erp.common.pojo.dto.*;
import com.hzb.erp.common.pojo.vo.LessonTeacherStatsVO;
import com.hzb.erp.common.pojo.vo.LessonVO;
import com.hzb.erp.common.service.*;
import com.hzb.erp.service.ImportExportService;
import com.hzb.erp.service.NotificationService;
import com.hzb.erp.service.notification.NoticeCodeEnum;
import com.hzb.erp.service.notification.bo.*;
import com.hzb.erp.utils.EnumTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课次表 服务实现类
 * </p>
 *
 * @author 541720500@qq.com
 */
@Service
@Slf4j
public class LessonServiceImpl extends ServiceImpl<LessonMapper, Lesson> implements LessonService {

    @Autowired
    private LessonTeacherService lessonTeacherService;
    @Autowired
    @Lazy
    private NotificationService notificationService;
    @Autowired
    private ClassStudentService classStudentService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private LessonStudentService lessonStudentService;
    @Autowired
    private LessonStudentMapper lessonStudentMapper;
    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentLessonCountLogService studentLessonCountLogService;

    @Resource
    private LessonMapper lessonMapper;

    @Autowired
    private ImportExportService importExportService;

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private TeachEvaluationService teachEvaluationService;
    @Autowired
    private LessonScheduleMapper lessonScheduleMapper;

    @Override
    public IPage<LessonVO> getList(LessonParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public List<LessonVO> getAll(LessonParamDTO param) {
        return this.baseMapper.getList(param);
    }

    @Override
    public List<Staff> getTeachers(Lesson lesson) {
        QueryWrapper<LessonTeacher> qw = new QueryWrapper<>();
        qw.eq("lesson_id", lesson.getId());
        List<LessonTeacher> list = lessonTeacherService.list(qw);
        if (list == null || list.size() == 0) {
            return null;
        }
        QueryWrapper<Staff> qw1 = new QueryWrapper<>();
        qw1.in("id", list.stream().map(LessonTeacher::getTeacherId).collect(Collectors.toList()));
        return staffService.list(qw1);
    }

    @Override
    public List<Student> getStudents(Lesson lesson) {
        List<Student> res = new ArrayList<>();
        List<Long> lessonStudentIds = lessonStudentMapper.getStudentIds(lesson.getId());
        if (lessonStudentIds == null || lessonStudentIds.size() == 0) {
            return res;
        }
        QueryWrapper<Student> qw1 = new QueryWrapper<>();
        qw1.in("id", lessonStudentIds);
        res = studentService.list(qw1);
        return res;
    }

    @Override
    public Boolean deleteBySchedule(List<Long> ids) {
        QueryWrapper<Lesson> qw = new QueryWrapper<>();
        qw.in("schedule_id", ids);
        // 删除签到记录
        List<Lesson> lessonList = list(qw);

        // 删除签到记录
        for (Lesson lesson : lessonList) {
            QueryWrapper<LessonStudent> qw1 = new QueryWrapper<>();
            qw1.eq("lesson_id", lesson.getId());
            lessonStudentService.remove(qw1);
            // 删除课次记录
            removeById(lesson.getId());
        }

        // 更新排课计划为未生成
        for (Long id : ids) {
            LessonSchedule schedule = lessonScheduleMapper.selectById(id);
            schedule.setState(false);
            lessonScheduleMapper.updateById(schedule);
        }

        return true;
    }

    @Override
    public IPage<LessonTeacherStatsVO> statsByTeachers(LessonParamDTO param) {
        return this.baseMapper.statsByTeachers(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public Boolean deleteLesson(List<Long> ids) {
        return this.removeByIds(ids);
    }

    @Override
    public LessonVO getInfo(Long id) {
        return this.baseMapper.getInfo(id);
    }

    @Override
    @Transactional
    public Boolean saveOrUpdateByDTO(LessonSaveDTO dto) {

        if (dto.getStartTime().isAfter(dto.getEndTime())) {
            throw new BizException("上课时间设置有误");
        }

        lessonTeacherService.validateTeacherIds(dto.getTeacherIds(), dto.getAssistantIds());
        boolean isUpdate = dto.getId() != null;

        Lesson item = new Lesson();
        BeanUtils.copyProperties(dto, item);

        Clazz clazz = clazzService.getById(dto.getClassId());
        Course course = courseService.getById(clazz.getCourseId());

        item.setCourseId(clazz.getCourseId());
        item.setTeacherId(dto.getTeacherIds().get(0));
        item.setBooking(course.getBooking() ? course.getBooking() : false);
        this.saveOrUpdate(item);

        if (isUpdate) {
            lessonTeacherService.deleteByLessonId(item.getId());
        }

        // 保存老师分表
        List<LessonTeacher> teacherList = new ArrayList<>();
        for (Long teacherId : dto.getTeacherIds()) {
            LessonTeacher lt = new LessonTeacher();
            lt.setTeacherId(teacherId);
            lt.setLessonId(item.getId());
            lt.setTypeNum(TeacherTypeEnum.TEACHER.getCode());
            teacherList.add(lt);
        }
        if (dto.getAssistantIds() != null) {
            for (Long teacherId : dto.getAssistantIds()) {
                LessonTeacher lt = new LessonTeacher();
                lt.setTeacherId(teacherId);
                lt.setLessonId(item.getId());
                lt.setTypeNum(TeacherTypeEnum.ASSISTANT.getCode());
                teacherList.add(lt);
            }
        }
        lessonTeacherService.saveBatch(teacherList);

        if (!isUpdate) {
            // 生成学生记录
            List<Student> studentList = classStudentService.getStudentsByClassId(item.getClassId());
            List<LessonStudent> lsList = new ArrayList<>();
            for (Student student : studentList) {
                LessonStudent newLs = new LessonStudent();
                newLs.setLessonId(item.getId());
                newLs.setClassId(clazz.getId());
                newLs.setStudentId(student.getId());
                newLs.setSignState(SignStateEnum.NONE);
                newLs.setConsumeCourseId(clazz.getCourseId());
                newLs.setCounselor(student.getCounselor());
                lsList.add(newLs);
            }
            lessonStudentService.saveBatch(lsList);
        }

        return true;
    }

    @Override
    public Boolean createQuickly(LessonSaveQuicklyDTO dto, Long creatorId) {

        if (dto.getStartTime().isAfter(dto.getEndTime())) {
            throw new BizException("上课时间设置有误");
        }

        Course course = courseService.getById(dto.getCourseId());
        List<Long> teacherIds = dto.getTeacherIds();

        // 创建班级
        Clazz clazz = new Clazz();
        clazz.setName(course.getName() + " *");
        clazz.setCourseId(dto.getCourseId());
        clazz.setClassroomId(dto.getRoomId());
        clazz.setTeacherId(teacherIds.get(0));
        clazz.setBeOver(false);
        clazz.setOverTime(dto.getDate().atTime(23, 59));
        clazz.setStartDate(dto.getDate());
        clazz.setEndDate(dto.getDate());
        clazz.setPlannedLessonCount(1);
        clazz.setPlannedStudentCount(dto.getStudentIds().size());
        clazzService.save(clazz);

        // 创建班级的学生
        ClassStudentAddDTO classStudentAddDTO = new ClassStudentAddDTO();
        classStudentAddDTO.setClassId(clazz.getId());
        classStudentAddDTO.setStudentIds(dto.getStudentIds());
        classStudentService.addClassStudents(classStudentAddDTO, creatorId);

        // 添加课程
        LessonSaveDTO lessonSaveDTO = new LessonSaveDTO();
        lessonSaveDTO.setTitle(course.getName());
        lessonSaveDTO.setTeacherIds(dto.getTeacherIds());
        lessonSaveDTO.setAssistantIds(dto.getAssistantIds());
        lessonSaveDTO.setClassId(clazz.getId());
        lessonSaveDTO.setRoomId(dto.getRoomId());
        lessonSaveDTO.setDate(dto.getDate());
        lessonSaveDTO.setDecCount(dto.getDecCount());
        lessonSaveDTO.setStartTime(dto.getStartTime());
        lessonSaveDTO.setEndTime(dto.getEndTime());
        lessonSaveDTO.setBooking(dto.getBooking() ? dto.getBooking() : false);
        return saveOrUpdateByDTO(lessonSaveDTO);
    }

    @Override
    public Boolean stopLesson(List<Long> ids) {
        List<Lesson> list = this.listByIds(ids);
        for (Lesson item : list) {
            item.setState(LessonStateEnum.STOPPED);
            sendLessonStateChangeNotice(item, LessonStateEnum.STOPPED);
        }
        return this.updateBatchById(list);
    }

    @Override
    public Boolean reopenLesson(List<Long> ids) {
        List<Lesson> list = this.listByIds(ids);
        for (Lesson item : list) {
            item.setState(LessonStateEnum.UNDERWAY);
            sendLessonStateChangeNotice(item, LessonStateEnum.UNDERWAY);
        }
        return this.updateBatchById(list);
    }

    /**
     * 发送调课通知
     */
    private void sendLessonStateChangeNotice(Lesson lesson, LessonStateEnum state) {

        List<Student> students = getStudents(lesson);
        List<Staff> teachers = getTeachers(lesson);

        LessonChangeBO bo = new LessonChangeBO();
        bo.setLessonTitle(lesson.getTitle());
        bo.setDate(String.valueOf(lesson.getDate()));
        bo.setStartTime(String.valueOf(lesson.getStartTime()));
        bo.setEndTime(String.valueOf(lesson.getEndTime()));
        bo.setNewState(state.getDist());
        bo.setSubject("调课通知：" + state.getDist());

        if (students != null && students.size() > 0) {
            for (Student s : students) {
                bo.setStudentName(s.getName());
                bo.setTeacherName("");
                bo.setContent(s.getName() + "同学：你的课程《" + lesson.getTitle() + "》状态变为：" + state.getDist());
                notificationService.sendToStudent(NoticeCodeEnum.STUDENT_LESSON_ONCHANGE, bo, s);
            }
        }
        if (teachers != null && teachers.size() > 0) {
            for (Staff t : teachers) {
                bo.setStudentName("");
                bo.setTeacherName(t.getName());
                bo.setContent(t.getName() + "老师：课程《" + lesson.getTitle() + "》状态变为" + state.getDist());
                notificationService.sendToTeacher(NoticeCodeEnum.TEACHER_LESSON_ONCHANGE, bo, t);
            }
        }

    }

    @Override
    public Boolean studentSign(Long lessonId, Long studentId, SignStateEnum state) {
        Lesson lesson = this.getById(lessonId);
        LessonStudent res = lessonStudentService.addOne(lessonId, studentId, lesson.getClassId(), lesson.getDecCount(), state, SignTypeEnum.STUDENT, null);
        // 课次变动日志
        if (res.getDecLessonCount() > 0) {
            studentLessonCountLogService.addOneByLesson(res, null);
            sendLessonSignNotice(lesson, state, studentId);
        }
        return true;
    }

//    @Override
//    public Boolean rollCall(Long teacherId, Long lessonId, Long studentId, SignStateEnum state) {
//        Lesson lesson = this.getById(lessonId);
//        LessonStudent res = lessonStudentService.addOne(lessonId, studentId, lesson.getClassId(), lesson.getDecCount(), state, SignTypeEnum.TEACHER, teacherId);
//        // 发送通知
//        // 课次变动日志
//        if (res.getDecLessonCount() > 0) {
//            studentLessonCountLogService.addOneByLesson(res, teacherId);
//            sendLessonSignNotice(lesson, state, studentId);
//        }
//        return true;
//    }

    @Override
    public Boolean rollCallBatch(Long teacherId, List<LessonSignSaveDTO> signData) {

        for (LessonSignSaveDTO item : signData) {
            Lesson lesson = this.getById(item.getLessonId());
            SignStateEnum state = EnumTools.getByCode(item.getState(), SignStateEnum.class);
            LessonStudent res = lessonStudentService.addOne(item.getLessonId(), item.getStudentId(), lesson.getClassId(), lesson.getDecCount(), state, SignTypeEnum.TEACHER, teacherId);
            if (state != null && res.getDecLessonCount() > 0) {
                // 课次变动日志
                studentLessonCountLogService.addOneByLesson(res, teacherId);
                sendLessonSignNotice(lesson, state, item.getStudentId());
            }
        }
        return true;
    }


    /**
     * 发送点名签到消息
     */
    private void sendLessonSignNotice(Lesson lesson, SignStateEnum state, Long studentId) {

        Student student = studentService.getById(studentId);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd HH:mm");

        StudentSignBO bo = new StudentSignBO();
        bo.setLessonTitle(lesson.getTitle());
        bo.setDate(lesson.getDate().toString());
        bo.setTime(lesson.getStartTime().toString());
        bo.setState(state.getDist());
        bo.setStudentName(student.getName());
        bo.setSubject("签到通知");
        bo.setContent(student.getName() + "同学：你的课程《" + lesson.getTitle() + "》已签到");

        notificationService.sendToStudent(NoticeCodeEnum.STUDENT_SIGN, bo, student);

    }

    @Override
    public SignStateEnum getSignStateByNow(Long lessonId) {

        Lesson lesson = this.getById(lessonId);
        if (lesson == null) {
            throw new BizException("未知课次");
        }
        SignStateEnum state;

        // 只能签到金额的约束：
//        if(!LocalDate.now().isEqual(lesson.getDate())) {
//            throw new BizException("只能签到今天的课次，请联系老师补签");
//        }
//        if(LocalTime.now().isBefore(lesson.getStartTime())) {
//            state = SignStateEnum.NORMAL;
//        } else {
//            state = SignStateEnum.LATE;
//        }

        // 不是今天的也可以签到：
        if (LocalDate.now().isBefore(lesson.getDate())) {
            state = SignStateEnum.NORMAL;
        } else if (LocalDate.now().isAfter(lesson.getDate())) {
            state = SignStateEnum.LATE;
        } else {
            if (LocalTime.now().isBefore(lesson.getStartTime())) {
                state = SignStateEnum.NORMAL;
            } else {
                state = SignStateEnum.LATE;
            }
        }

        return state;
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void generateLessonStudent(Integer minutes) {
//        // 找到时间分钟范围内开课的课次
//        List<Lesson> list = lessonMapper.cronLessonToGenerateStudentRel(minutes);
//        for (Lesson les : list) {
//
//            // 按班级找到没有生成签到记录的班级学生关联数据
//            List<ClassStudent> csList = classStudentMapper.selectClassStudentsByLessonId(les.getId(), false);
//            if (csList == null || csList.size() == 0) {
//                continue;
//            }
//
//            // 生成签到记录
//            List<LessonStudent> insertList = new ArrayList<>();
//            for (ClassStudent cs : csList) {
//                LessonStudent newLs = new LessonStudent();
//                newLs.setLessonId(les.getId());
//                newLs.setTeacherId(null);
//                newLs.setClassId(cs.getClassId());
//                newLs.setStudentId(cs.getStudentId());
//                insertList.add(newLs);
//            }
//            if (insertList.size() > 0) {
//                lessonStudentService.saveBatch(insertList);
//            }
//            log.info("生成签到记录数：{}", insertList.size());
//        }
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void closeLesson() {
        List<Lesson> list = lessonMapper.cronLessonToClose();
        if (list != null && list.size() > 0) {
            List<Long> ids = new ArrayList<>();
            for (Lesson les : list) {
                ids.add(les.getId());
            }
            lessonMapper.updateStateBatch(ids, 2);

            System.out.println("结课次数：" + list.size());
        }
    }

    @Override
    public void exportStatisData(LessonParamDTO param) {
        List<LessonTeacherStatsVO> list = this.baseMapper.statsByTeachers(param);
        LinkedHashMap<String, String> header = new LinkedHashMap<String, String>() {{
            put("year", "年份");
            put("month", "月份");
            put("teacherName", "姓名");
            put("classFee", "单节上课费");
            put("teacherCount", "上课课次数");
            put("totalClassFee", "小计");
            put("assistantFee", "单节助教费");
            put("assistantCount", "助教课次数");
            put("totalAssistantFee", "小计");
        }};
        importExportService.exportExcel(header, list, "课时费统计");
    }

    @Override
    public void exportLessonData(LessonParamDTO param) {
        List<LessonVO> list = getAll(param);
        LinkedHashMap<String, String> header = new LinkedHashMap<String, String>() {{
            put("date", "上课日期");
            put("startTime", "开始时间");
            put("endTime", "结束时间");
            put("title", "标题");
            put("courseName", "课程");
            put("className", "班级名称");
            put("lessonType", "类型");
            put("teacherNames", "上课老师");
            put("assistantNames", "助教");
            put("classroom", "教室");
            put("studentNum", "学生数");
            put("studentSignNum", "签到数");
            put("decCount", "消课数");
            put("state", "状态");
        }};
        importExportService.exportExcel(header, list, "导出课表");
    }

    @Override
    public void lessonNotice() {
        // 找到明天上课的课次
        QueryWrapper<Lesson> qw = new QueryWrapper<>();
        qw.eq("date", LocalDate.now().plusDays(1)).eq("state", LessonStateEnum.UNDERWAY.getCode());
        List<Lesson> list = this.list(qw);

        if (list == null || list.size() == 0) {
            return;
        }

        for (Lesson l : list) {
            List<Staff> teachers = getTeachers(l);
            List<Student> students = getStudents(l);
            Clazz clazz = clazzService.getById(l.getClassId());
            Classroom classroom = classroomService.getById(l.getRoomId());

            LessonStartBO bo = new LessonStartBO();
            bo.setCourseName(l.getTitle());
            bo.setDate(l.getDate().toString());
            bo.setStartTime(l.getStartTime().toString());
            bo.setEndTime(l.getEndTime().toString());
            bo.setSubject("上课通知");

            if (clazz != null) {
                bo.setClassName(clazz.getName());
            } else {
                bo.setClassName("");
            }
            if (classroom != null) {
                bo.setClassroom(classroom.getName());
                bo.setClassroom("");
            }

            if (teachers != null && teachers.size() > 0) {
                for (Staff t : teachers) {
                    bo.setStudentName("");
                    bo.setTeacherName(t.getName());
                    bo.setContent(t.getName() + "老师你好，请注意上课时间：" + l.getDate().toString() + " " + l.getStartTime().toString() + "~" + l.getEndTime().toString());
                    notificationService.sendToTeacher(NoticeCodeEnum.TEACHER_LESSON_START, bo, t);
                }
            }


            if (students != null && students.size() > 0) {
                for (Student s : students) {
                    bo.setStudentName(s.getName());
                    bo.setTeacherName("");
                    bo.setContent(s.getName() + "同学你好，请注意上课时间：" + l.getDate().toString() + " " + l.getStartTime().toString() + "~" + l.getEndTime().toString());
                    notificationService.sendToStudent(NoticeCodeEnum.STUDENT_LESSON_START, bo, s);
                }
            }

        }
    }

    @Override
    public void lessonLessWarning() {
        List<StudentCourse> list = studentCourseService.getWarningList();
        if (list == null || list.size() == 0) {
            return;
        }
        for (StudentCourse l : list) {
            Course course = courseService.getById(l.getCourseId());
            Student student = studentService.getById(l.getStudentId());
            if (student == null) {
                continue;
            }

            LessonNotEnoughBO bo = new LessonNotEnoughBO();
            bo.setCourseName(course.getName());
            bo.setLessonCount(String.valueOf(studentCourseService.getLessonRemainingCount(l)));
            bo.setExpireDate(l.getExpireDate().toString());
            bo.setStudentName(student.getName());
            bo.setTeacherName("");
            bo.setSubject("课时不足通知");
            bo.setContent(student.getName() + "你好，你的课程《" + course.getName() + "》课时不足");

            notificationService.sendToStudent(NoticeCodeEnum.STUDENT_LESSON_COUNT_LESS, bo, student);

            Staff staff = staffService.getById(student.getCounselor());
            if (staff == null) {
                continue;
            }
            bo.setTeacherName(staff.getName());
            bo.setStudentName(student.getName());
            bo.setContent(staff.getName() + "老师你好，学生" + student.getName() + "的课程《" + course.getName() + "》课时不足，请留意。");
            notificationService.sendToTeacher(NoticeCodeEnum.TEACHER_STUDENT_LESSONLESS, bo, staff);

            // 增加已提醒次数
            l.setWarningTimes(l.getWarningTimes() == null ? 1 : l.getWarningTimes() + 1);
            studentCourseService.updateById(l);
        }
    }

    @Override
    public Boolean studentLeaveApply(Long studentId, Long lessonId) {

        Lesson lesson = getById(lessonId);
        Student student = studentService.getById(studentId);

        QueryWrapper<LessonStudent> qw = new QueryWrapper<>();
        qw.eq("lesson_id", lessonId).eq("student_id", studentId);
        LessonStudent one = lessonStudentService.getOne(qw);
        if (one == null) {
            one = new LessonStudent();
            one.setLessonId(lessonId);
            one.setClassId(lesson.getClassId());
            one.setStudentId(studentId);
            one.setDecLessonCount(0);
            one.setSignTime(LocalDateTime.now());
            one.setSignType(SignTypeEnum.STUDENT);
            one.setSignState(SignStateEnum.LEAVE);
            one.setCounselor(student.getCounselor());
            if (!lessonStudentService.save(one)) {
                return false;
            }
        } else {
            one.setSignTime(LocalDateTime.now());
            one.setSignType(SignTypeEnum.STUDENT);
            one.setSignState(SignStateEnum.LEAVE);
            if (!lessonStudentService.updateById(one)) {
                return false;
            }
        }
        studentLeaveNoticeToTeacher(lesson, student);
        return true;
    }

    @Override
    public boolean changeCourseAtSign(Long lessonId, Long studentId, Long courseId) {
        Lesson lesson = getById(lessonId);
        QueryWrapper<LessonStudent> qw = new QueryWrapper<>();
        qw.eq("lesson_id", lessonId).eq("student_id", studentId);
        LessonStudent one = lessonStudentService.getOne(qw);
        Student student = studentService.getById(studentId);
        if (one == null) {
            one = new LessonStudent();
            one.setLessonId(lessonId);
            one.setStudentId(studentId);
            one.setClassId(lesson.getClassId());
            one.setConsumeCourseId(courseId);
            one.setCounselor(student.getCounselor());
            return lessonStudentService.save(one);
        } else {
            if (one.getDecLessonCount() != null && one.getDecLessonCount() > 0) {
                throw new BizException("已消课的无法更改消费课程");
            }
            one.setConsumeCourseId(courseId);
            return lessonStudentService.updateById(one);
        }
    }

    private void studentLeaveNoticeToTeacher(Lesson lesson, Student student) {

        if (lesson == null || student == null) {
            return;
        }
        StudentLeaveBO bo = new StudentLeaveBO();
        bo.setLessonTitle(lesson.getTitle());
        bo.setDate(lesson.getDate().toString());
        bo.setStartTime(lesson.getStartTime().toString());
        bo.setStudentName(student.getName());
        bo.setSubject("请假通知");

        List<Staff> teachers = getTeachers(lesson);

        if (teachers != null && teachers.size() > 0) {
            for (Staff t : teachers) {
                bo.setTeacherName(t.getName());
                bo.setContent(t.getName() + "老师你好，你的课程《" + lesson.getTitle() + "》有学生请假：" + student.getName());
                notificationService.sendToTeacher(NoticeCodeEnum.TEACHER_STUDENT_LEAVE, bo, t);
            }
        }

    }

    @Override
    public boolean teachEvaluate(TeachEvaluateDTO dto, Long studentId) {

        Lesson lesson = getById(dto.getLessonId());
        List<Staff> teachers = getTeachers(lesson);

        List<TeachEvaluation> list = new ArrayList<>();
        for (Staff teacher : teachers) {
            TeachEvaluation item = new TeachEvaluation();
            BeanUtils.copyProperties(dto, item);

            item.setAddTime(LocalDateTime.now());
            item.setTeacherId(teacher.getId());
            item.setStudentId(studentId);
            list.add(item);
        }
        QueryWrapper<TeachEvaluation> qw = new QueryWrapper<>();
        qw.eq("lesson_id", dto.getLessonId()).eq("student_id", studentId);
        teachEvaluationService.remove(qw);

        return teachEvaluationService.saveBatch(list);
    }

    @Override
    public boolean addStudents(LessonStudentAddDTO dto, Long currentUserId) {
        List<LessonStudent> list = new ArrayList<>();
        for (Long studentId : dto.getStudentIds()) {
            QueryWrapper<LessonStudent> qw = new QueryWrapper<>();
            qw.eq("lesson_id", dto.getLessonId()).eq("student_id", studentId).last("limit 1");
            LessonStudent item = lessonStudentService.getOne(qw);
            Student student = studentService.getById(studentId);
            if (item == null) {
                LessonStudent ls = new LessonStudent();
                ls.setLessonId(dto.getLessonId());
                ls.setStudentId(studentId);
                ls.setSignState(SignStateEnum.NONE);
                ls.setCounselor(student.getCounselor());
                list.add(ls);
            }
        }
        return list.size() > 0 && lessonStudentService.saveBatch(list);
    }

}
