package com.hzb.erp.adminCenter;


import com.hzb.erp.annotation.Log;
import com.hzb.erp.annotation.PreventMultiSubmit;
import com.hzb.erp.common.pojo.dto.LessonEvaluateSaveDTO;
import com.hzb.erp.common.pojo.dto.LessonStudentParamDTO;
import com.hzb.erp.common.pojo.dto.StudentLessonLogParamDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.LessonStudentService;
import com.hzb.erp.common.service.StudentLessonCountLogService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.service.UserAuthService;
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
 * 课时学员关联表 前端控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/lessonStudent")
@Api(value = "课时学员关联", tags = "课时学员关联")
public class LessonStudentController {

    @Autowired
    private LessonStudentService lessonStudentService;

    @Autowired
    private StudentLessonCountLogService studentLessonCountLogService;

    @ApiOperation("消课记录")
    @GetMapping("/lessonCountLog")
    public PaginationVO lessonCountLog(@RequestParam(value = "page", defaultValue = "1") Integer page, // 若为null则是查全部
                                       @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                                       @RequestParam(value = "studentId", defaultValue = "") Long studentId) {

        StudentLessonLogParamDTO param = new StudentLessonLogParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setStudentId(studentId);

        return JsonResponseUtil.paginate(studentLessonCountLogService.getList(param));
    }

    @ApiOperation("点评记录")
    @GetMapping("/evaluateLog")
    public Object evaluateLog(@RequestParam(value = "page", defaultValue = "") Integer page,
                              @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                              @RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "lessonId", defaultValue = "") Long lessonId,
                              @RequestParam(value = "teacherId", defaultValue = "") Long teacherId,
                              @RequestParam(value = "studentId", defaultValue = "") Long studentId,
                              @RequestParam(value = "onlyEvaluate", defaultValue = "true") Boolean onlyEvaluate,
                              @RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                              @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        LessonStudentParamDTO param = new LessonStudentParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setLessonId(lessonId);
        param.setStudentId(studentId);
        param.setEvaluateTeacherId(teacherId);
        param.setStartDate(startDate);
        param.setEndDate(endDate);
        param.setKeyword(keyword);
        param.setOnlyEvaluate(onlyEvaluate);
        return page != null && page > 0 ?
                JsonResponseUtil.paginate(lessonStudentService.getList(param)) :
                lessonStudentService.getAll(param);
    }

    @ApiOperation("上课点评")
    @Log(description = "上课点评", type = "课次管理")
    @PostMapping("/evaluation")
    @PreventMultiSubmit
    public JsonResponse evaluation(@Valid @RequestBody LessonEvaluateSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (lessonStudentService.evaluation(dto, UserAuthService.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("消课返还")
    @Log(description = "消课返还", type = "课次管理")
    @PostMapping("/rollbackCourseNum")
    public JsonResponse rollbackCourseNum(@RequestBody List<Long> ids) {
        if (lessonStudentService.rollbackCourseNum(ids, UserAuthService.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

}
