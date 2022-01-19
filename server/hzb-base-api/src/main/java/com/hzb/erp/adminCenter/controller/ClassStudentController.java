package com.hzb.erp.adminCenter.controller;


import com.hzb.erp.annotation.Log;
import com.hzb.erp.common.pojo.dto.ClassStudentAddDTO;
import com.hzb.erp.common.service.ClassStudentService;
import com.hzb.erp.common.service.LessonService;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 班级学员表 前端控制器
 * </p>
 *
 * @author 541720500@qq.com
 */
@RestController
@RequestMapping("/common/classStudent")
@Api(value = "班级学员管理", tags = "班级学员管理")
public class ClassStudentController {

    @Autowired
    private ClassStudentService classStudentService;
    @Autowired
    private LessonService lessonService;

    @ApiOperation("班级添加单个学员")
    @Log(description = "班级添加单个学员")
    @GetMapping("/addStudent")
    public JsonResponse addStudent(@RequestParam(value = "classId") Long classId,
                                   @RequestParam(value = "studentId") Long studentId) {

        if (classStudentService.addClassStudent(classId, studentId, UserAuthService.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("添加失败");
        }
    }

    @ApiOperation("班级批量添加学员")
    @Log(description = "班级批量添加学员", type = "学员管理")
    @PostMapping("/addStudents")
    public JsonResponse addStudents(@RequestBody ClassStudentAddDTO dto) {
        classStudentService.addClassStudents(dto, UserAuthService.getCurrentUserId());
        return JsonResponseUtil.success();
    }

    @ApiOperation("班级移除单个学员")
    @Log(description = "班级移除单个学员", type = "班级学员管理")
    @GetMapping("/deleteClassStudent")
    public JsonResponse deleteClassStudent(@RequestParam(value = "classId") Long classId,
                                           @RequestParam(value = "studentId") Long studentId) {

        if (classStudentService.deleteClassStudent(classId, studentId)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("提交失败");
        }
    }

    @ApiOperation("班级批量移除学员")
    @Log(description = "班级批量移除学员", type = "班级学员管理")
    @PostMapping("/deleteByIds")
    public JsonResponse deleteByIds(@RequestBody List<Long> ids) {
        if (classStudentService.deleteClassStudentByIds(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

    @ApiOperation("更换默认消费课程")
    @Log(description = "更换默认消费课程", type = "班级学员管理")
    @GetMapping("/changeConsumeCourse")
    public JsonResponse changeConsumeCourse(@RequestParam("consumeCourseId") Long consumeCourseId,
                                            @RequestParam("classStudentId") Long classStudentId) {
        if (classStudentService.changeConsumeCourse(classStudentId, consumeCourseId)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("提交失败");
        }
    }

    @ApiOperation("修改签到消课课程")
    @Log(description = "修改签到消课课程", type = "班级学员管理")
    @GetMapping("/changeCourseAtSign")
    public JsonResponse changeCourseAtSign(@RequestParam("lessonId") Long lessonId,
                                           @RequestParam("studentId") Long studentId,
                                           @RequestParam("courseId") Long courseId) {
        if (lessonService.changeCourseAtSign(lessonId, studentId, courseId)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("提交失败");
        }
    }

}
