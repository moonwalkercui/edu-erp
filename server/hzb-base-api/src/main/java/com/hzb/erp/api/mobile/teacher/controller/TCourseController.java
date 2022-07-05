package com.hzb.erp.api.mobile.teacher.controller;

import com.hzb.erp.api.pc.clazz.service.ClazzService;
import com.hzb.erp.api.pc.course.service.CourseService;
import com.hzb.erp.api.pc.student.service.StudentCourseService;
import com.hzb.erp.api.pc.student.service.StudentLessonCountLogService;
import com.hzb.erp.api.pc.student.service.StudentService;
import com.hzb.erp.api.base.annotation.Log;
import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.api.pc.course.pojo.CourseParamDTO;
import com.hzb.erp.api.pc.student.pojo.StudentCourseParamDTO;
import com.hzb.erp.api.pc.student.pojo.StudentCourseSaveDTO;
import com.hzb.erp.api.pc.course.pojo.CourseVO;
import com.hzb.erp.common.pojo.PaginationVO;
import com.hzb.erp.api.pc.student.pojo.StudentCourseVO;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.api.base.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@RestController
@RequestMapping("/tCenter/course")
@Api(value = "课程数据", tags = "课程数据")
public class TCourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private StudentLessonCountLogService studentLessonCountLogService;

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private StudentService studentService;


    @ApiOperation("课程信息")
    @GetMapping("/info")
    public CourseVO info(@RequestParam(value = "id") Long id) {
        return courseService.getInfo(id);
    }

    @ApiOperation("课程列表")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "") Integer page,
                       @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                       @RequestParam(value = "name", defaultValue = "") String name,
                       @RequestParam(value = "subjectId", defaultValue = "") Long subjectId) {
        CourseParamDTO param = new CourseParamDTO();
        param.setSubjectId(subjectId);
        List<Integer> value = new ArrayList<Integer>() {{
            add(1);
        }};
        param.setState(value);
        param.setName(name);
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setWithFavRate(true);

        return page != null && page > 0 ?
                JsonResponseUtil.paginate(courseService.getList(param)) :
                courseService.getAll(param);
    }


    @ApiOperation("新增和编辑报名信息")
    @Log(description = "新增和编辑报名信息", type = "报名管理")
    @PostMapping("/saveCourse")
    public JsonResponse saveCourse(@Valid @RequestBody StudentCourseSaveDTO postData, BindingResult result) {

        Student student = studentService.getById(postData.getStudentId());
        if (student == null) {
            return JsonResponseUtil.error("学员选择错误");
        }
        CommonUtil.handleValidMessage(result);
        if (postData.getId() != null) {
            studentCourseService.editOne(postData);
        } else {
            studentCourseService.addOne(postData, UserAuthService.getCurrentUserId());
            studentLessonCountLogService.addOneByContract(postData.getStudentId(), postData.getCourseId(), postData.getCountLessonTotal(), UserAuthService.getCurrentUserId());
            clazzService.autoCreateOne2One(studentService.getById(postData.getStudentId()), courseService.getById(postData.getCourseId()), UserAuthService.getCurrentUserId());
        }
        return JsonResponseUtil.success();
    }

    @ApiOperation("保单记录")
    @GetMapping("/contractRecord")
    public PaginationVO contractRecord(@RequestParam(value = "page", defaultValue = "") Integer page,
                                       @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize) {
        StudentCourseParamDTO param = new StudentCourseParamDTO();
        param.setOperator(UserAuthService.getCurrentUserId());
        param.setPage(page);
        param.setPageSize(pageSize);
        return JsonResponseUtil.paginate(studentCourseService.getList(param));
    }

    @ApiOperation("保单记录")
    @GetMapping("/contractInfo")
    public StudentCourseVO contractInfo(@RequestParam(value = "id") Long id) {
        return studentCourseService.getInfo(id);
    }
}
