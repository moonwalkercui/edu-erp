package com.hzb.erp.api.pc.student.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.clazz.service.ClazzService;
import com.hzb.erp.api.pc.course.service.CourseService;
import com.hzb.erp.api.pc.student.service.StudentCourseService;
import com.hzb.erp.api.pc.student.service.StudentLessonCountLogService;
import com.hzb.erp.api.pc.student.service.StudentService;
import com.hzb.erp.api.base.annotation.Log;
import com.hzb.erp.api.base.annotation.PreventMultiSubmit;
import com.hzb.erp.api.pc.student.mapper.StudentCourseMapper;
import com.hzb.erp.api.pc.finance.pojo.PayOverdueDTO;
import com.hzb.erp.api.pc.student.pojo.StudentCourseParamDTO;
import com.hzb.erp.api.pc.student.pojo.StudentCourseSaveDTO;
import com.hzb.erp.api.pc.student.pojo.StudentCourseVO;
import com.hzb.erp.api.base.service.UserAuthService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 学员合约表 前端控制器
 * </p>
 *
 * @author 541720500@qq.com
 */
@RestController
@RequestMapping("/common/studentCourse")
@Api(value = "学员课程", tags = "学员课程")
public class StudentCourseController {

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private StudentLessonCountLogService studentLessonCountLogService;

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;


    @ApiOperation("学员合约信息")
    @GetMapping("/info")
    public StudentCourseVO studentInfo(@RequestParam("id") Long id) {
        return studentCourseService.getInfo(id);
    }

    @ApiOperation("学员合约记录,课时统计")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "") Integer page,
                       @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                       @RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                       @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                       @RequestParam(value = "expiredStart", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate expiredStart,
                       @RequestParam(value = "expiredEnd", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate expiredEnd,
                       @RequestParam(value = "keyword", defaultValue = "") String keyword,
                       @RequestParam(value = "studentName", defaultValue = "") String studentName,
                       @RequestParam(value = "subjectName", defaultValue = "") String subjectName,
                       @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
                       @RequestParam(value = "sortType", defaultValue = "") String sortType,
                       @RequestParam(value = "courseId", defaultValue = "") Long courseId,
                       @RequestParam(value = "verified", defaultValue = "false") Boolean verified,
                       @RequestParam(value = "self", defaultValue = "false") Boolean self,
                       @RequestParam(value = "export", defaultValue = "false") Boolean export,
                       @RequestParam(value = "studentId", defaultValue = "") Long studentId) {
        StudentCourseParamDTO param = new StudentCourseParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setSortBy(sortBy);
        param.setSortType(sortType);
        param.setKeyword(keyword);
        param.setStudentName(studentName);
        param.setStudentId(studentId);
        param.setCourseId(courseId);
        param.setSubjectName(subjectName);
        param.setStartDate(startDate);
        param.setEndDate(endDate);
        param.setExpiredStart(expiredStart);
        param.setExpiredEnd(expiredEnd);
        param.setVerified(verified);
        if (self != null && self) {
            param.setOperator(UserAuthService.getCurrentUserId());
        }
        if (export != null && export) {
            studentCourseService.exportExcel(param);
            return null;
        }
        return page == null ? studentCourseService.getAll(param)
                : JsonResponseUtil.paginate(studentCourseService.getList(param));
    }

    @ApiOperation("学员剩余有效课时数列表")
    @GetMapping("/validList")
    public Object validList(@RequestParam(value = "page", defaultValue = "") Integer page,
                            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                            @RequestParam(value = "studentId", defaultValue = "") Long studentId) {
        StudentCourseParamDTO param = new StudentCourseParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setStudentId(studentId);
        return page == null ? studentCourseMapper.getValidList(param)
                : JsonResponseUtil.paginate(studentCourseMapper.getValidList(new Page<>(param.getPage(), param.getPageSize()), param));
    }

    @ApiOperation("删除学员合约")
    @Log(description = "删除学员合约", type = "报名管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        studentCourseService.delete(ids);
        return JsonResponseUtil.success();
    }

    @ApiOperation("新增和编辑报名信息")
    @Log(description = "新增和编辑报名信息", type = "报名管理")
    @PostMapping("/saveCourse")
    @PreventMultiSubmit
    public JsonResponse saveCourse(@Valid @RequestBody StudentCourseSaveDTO postData, BindingResult result) {
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

    @ApiOperation("修改已完成课时")
    @Log(description = "修改已完成课时", type = "报名管理")
    @GetMapping("/editLessonCountRemaining")
    public JsonResponse editLessonCountRemaining(@RequestParam(value = "id") Long id,
                                                 @RequestParam(value = "countLessonComplete") Integer countLessonComplete,
                                                 @RequestParam(value = "remark", defaultValue = "") String remark) {
        studentCourseService.editLessonCountComplete(id, countLessonComplete, remark, UserAuthService.getCurrentUserId());
        return JsonResponseUtil.success();
    }

    @ApiOperation("修改学生的课程到期日")
    @Log(description = "修改学生的课程到期日", type = "报名管理")
    @GetMapping("/editLessonExpireDate")
    public JsonResponse editLessonExpireDate(@RequestParam(value = "id") Long id,
                                             @RequestParam(value = "expireDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate expireDate) {
        studentCourseService.editLessonExpireDate(id, expireDate, UserAuthService.getCurrentUserId());
        return JsonResponseUtil.success();
    }

    @ApiOperation("交欠费")
    @Log(description = "交欠费", type = "报名管理")
    @PostMapping("/payOverdue")
    public JsonResponse payOverdue(@Valid @RequestBody PayOverdueDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        studentCourseService.payOverdue(dto, UserAuthService.getCurrentUserId());
        return JsonResponseUtil.success();
    }

    @ApiOperation("修改消课优先级")
    @Log(description = "修改消课优先级", type = "报名管理")
    @GetMapping("/changePriority")
    public JsonResponse changePriority(@RequestParam(value = "id") Long id, @RequestParam(value = "value") Integer value) {
        studentCourseService.changePriority(id, value);
        return JsonResponseUtil.success();
    }

}
