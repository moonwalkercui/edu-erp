package com.hzb.erp.api.mobile.student.controller;

import com.hzb.erp.api.base.annotation.Log;
import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.api.pc.course.pojo.CourseTrialParamDTO;
import com.hzb.erp.api.pc.course.pojo.CourseTrialRecordParamDTO;
import com.hzb.erp.api.pc.student.pojo.StudentCourseParamDTO;
import com.hzb.erp.api.pc.course.service.CourseTrialRecordService;
import com.hzb.erp.api.pc.course.service.CourseTrialService;
import com.hzb.erp.api.pc.student.service.StudentCourseService;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.api.mobile.student.service.StudentAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@RestController
@RequestMapping("/sCenter/course")
@Api(value = "课程接口", tags = "课程接口")
public class SCourseController {

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private CourseTrialService courseTrialService;

    @Autowired
    private CourseTrialRecordService courseTrialRecordService;

    @ApiOperation("学员课次统计")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "") Integer page,
                       @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                       @RequestParam(value = "excludeExpired", defaultValue = "") Boolean excludeExpired,
                       @RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                       @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        StudentCourseParamDTO param = new StudentCourseParamDTO();
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        Long studentId = student.getId();
        param.setStudentId(studentId);
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setStartDate(startDate);
        param.setEndDate(endDate);
        param.setExcludeExpired(excludeExpired);
        return page != null && page > 0 ?
                JsonResponseUtil.paginate(studentCourseService.getList(param)) :
                studentCourseService.getAll(param);
    }

    /**
    * 体验卡列表
    * */
    @ApiOperation("体验卡列表")
    @GetMapping("/trialList")
    public Object trialList() {
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        CourseTrialParamDTO param = new CourseTrialParamDTO();
        param.setState(true);
        param.setExcludeEnd(true);
        param.setExcludeStudentId(student.getId());
        return courseTrialService.getAll(param);
    }

    /**
     * 我的体验卡列表
     * */
    @ApiOperation("我的体验卡列表")
    @GetMapping("/myTrialList")
    public Object myTrialList() {
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        CourseTrialRecordParamDTO param = new CourseTrialRecordParamDTO();
        param.setStudentId(student.getId());
        return courseTrialRecordService.getAll(param);
    }

    @ApiOperation("领取体验卡")
    @PostMapping("/catchTrial/{trialId}")
    @Log(description = "领取体验卡", type = "学生端", isStaff = false)
    public Object catchTrial(@PathVariable(value = "trialId") Long trialId) {
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return JsonResponseUtil.error("请先添加学生");
        }
        return courseTrialRecordService.getOne(trialId, student)
                ? JsonResponseUtil.success("领取成功")
                : JsonResponseUtil.error("领取失败");
    }

}
