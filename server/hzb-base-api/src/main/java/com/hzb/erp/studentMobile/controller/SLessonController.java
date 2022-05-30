package com.hzb.erp.studentMobile.controller;

import com.hzb.erp.base.annotation.Log;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.mapper.LessonMapper;
import com.hzb.erp.common.pojo.dto.LessonParamDTO;
import com.hzb.erp.common.pojo.dto.LessonStudentParamDTO;
import com.hzb.erp.common.pojo.dto.TeachEvaluateDTO;
import com.hzb.erp.common.pojo.vo.LessonVO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.LessonService;
import com.hzb.erp.common.service.LessonStudentService;
import com.hzb.erp.common.service.StudentService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.DateTool;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.studentMobile.service.StudentAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@RestController
@RequestMapping("/sCenter/lesson")
@Api(value = "课次", tags = "课次")
public class SLessonController {

    @Autowired
    private LessonService lessonService;

    @Resource
    private LessonMapper lessonMapper;

    @Resource
    private LessonStudentService lessonStudentService;

    @Autowired
    private StudentService studentService;

    @ApiOperation("课次信息")
    @GetMapping("/info")
    public LessonVO info(@RequestParam(value = "id") Long id) {
        return lessonService.getInfo(id);
    }

    @ApiOperation("课表")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "") Integer page, // 若为null则是查全部
                       @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                       @RequestParam(value = "isToday", defaultValue = "false") Boolean isToday,
                       @RequestParam(value = "bookable", defaultValue = "false") Boolean bookable, // 是否是可预约列表
                       @RequestParam(value = "date", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        LessonParamDTO param = buildParam(page, pageSize, isToday, date, bookable);
        return page != null && page > 0 ?
                JsonResponseUtil.paginate(lessonService.getList(param)) :
                lessonService.getAll(param);
    }

    private LessonParamDTO buildParam(Integer page, Integer pageSize, Boolean isToday, LocalDate date, Boolean bookable) {
        LessonParamDTO param = new LessonParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setBookable(bookable);
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        Long studentId = student.getId();
        param.setStudentId(studentId);
        if (isToday) {
            param.setDate(LocalDate.now());
        }
        if (date != null) {
            param.setDate(date);
        }
        return param;
    }

    @ApiOperation("日课表数量统计")
    @GetMapping("/lessonCountEveryDay")
    public List<Map<String, Object>> lessonCountEveryDay(@RequestParam(value = "date", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        LessonParamDTO param = new LessonParamDTO();
        param.setStartDate(DateTool.firstDayOfMonth(date));
        param.setEndDate(DateTool.lastDayOfMonth(date));
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        Long studentId = student.getId();
        param.setStudentId(studentId);
        return lessonMapper.getLessonNumEveryDay(param);
    }

    @ApiOperation("签到记录")
    @Log(description = "签到记录", type = "学生端", isStaff = false)
    @GetMapping("/signRecord")
    public PaginationVO rollCallRecord(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam(value = "courseId", defaultValue = "") Long courseId) {

        LessonStudentParamDTO param = new LessonStudentParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        Long studentId = student.getId();
        param.setStudentId(studentId);
        param.setCourseId(courseId);

        return JsonResponseUtil.paginate(lessonStudentService.getList(param));
    }

    @ApiOperation("签到")
    @Log(description = "签到", type = "学生端", isStaff = false)
    @PostMapping("/sign/{lessonId}")
    public JsonResponse sign(@PathVariable("lessonId") Long lessonId) {
        Student student = StudentAuthService.getCurrentStudent();
        Long studentId = student.getId();
        if (lessonService.studentSign(lessonId, studentId, lessonService.getSignStateByNow(lessonId))) {
            return JsonResponseUtil.success("签到完成");
        } else {
            return JsonResponseUtil.error("签到失败");
        }
    }

    @ApiOperation("点评记录")
    @GetMapping("/evaluateLog")
    public PaginationVO evaluateLog(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        Long studentId = student.getId();
        LessonStudentParamDTO param = new LessonStudentParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setStudentId(studentId);
        param.setOnlyEvaluate(true);
        student.setRedpointEvaluate(LocalDateTime.now());
        studentService.updateById(student);

        return JsonResponseUtil.paginate(lessonStudentService.getList(param));
    }

    @ApiOperation("授课评价")
    @Log(description = "授课评价", type = "学生端", isStaff = false)
    @PostMapping("/teachEvaluate")
    public JsonResponse teachEvaluate(@Valid @RequestBody TeachEvaluateDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        Student student = StudentAuthService.getCurrentStudent();
        if (lessonService.teachEvaluate(dto, student.getId())) {
            return JsonResponseUtil.success("已添加");
        } else {
            return JsonResponseUtil.error();
        }
    }

    @ApiOperation("预约")
    @Log(description = "预约", type = "学生端", isStaff = false)
    @PostMapping("/appoint/{lessonId}")
    public JsonResponse appoint(@PathVariable("lessonId") Long lessonId) {
        Student student = StudentAuthService.getCurrentStudent();
        Long studentId = student.getId();
        if (lessonService.studentAppoint(lessonId, studentId)) {
            return JsonResponseUtil.success("预约完成");
        } else {
            return JsonResponseUtil.error("预约失败");
        }
    }
}
