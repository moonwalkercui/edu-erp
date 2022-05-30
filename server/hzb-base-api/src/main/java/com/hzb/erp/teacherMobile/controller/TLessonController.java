package com.hzb.erp.teacherMobile.controller;

import com.hzb.erp.base.annotation.Log;
import com.hzb.erp.common.mapper.ClassStudentMapper;
import com.hzb.erp.common.pojo.dto.ClassStudentParamDTO;
import com.hzb.erp.common.pojo.dto.LessonEvaluateSaveDTO;
import com.hzb.erp.common.pojo.dto.LessonParamDTO;
import com.hzb.erp.common.pojo.dto.LessonStudentParamDTO;
import com.hzb.erp.common.pojo.vo.ClassStudentSignVO;
import com.hzb.erp.common.pojo.vo.LessonStudentVO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.ClassStudentService;
import com.hzb.erp.common.service.LessonService;
import com.hzb.erp.common.service.LessonStudentService;
import com.hzb.erp.adminCenter.service.UserAuthService;
import com.hzb.erp.teacherMobile.service.TLessonService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.DateTool;
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
import java.util.Map;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@RestController
@RequestMapping("/tCenter/lesson")
@Api(value = "课程课次", tags = "课程课次")
public class TLessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private TLessonService tLessonService;

    @Autowired
    private LessonStudentService lessonStudentService;

    @Autowired
    private ClassStudentService classStudentService;

    @Autowired
    private ClassStudentMapper classStudentMapper;

    @ApiOperation("课表")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "") Integer page, // 若为null则是查全部
                       @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                       @RequestParam(value = "isToday", defaultValue = "false") Boolean isToday,
                       @RequestParam(value = "date", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        LessonParamDTO param = new LessonParamDTO();
        if (isToday) {
            param.setDate(LocalDate.now());
        }
        if (date != null) {
            param.setDate(date);
        }
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setTeacherId(UserAuthService.getCurrentUserId());

        return page != null && page > 0 ?
                JsonResponseUtil.paginate(lessonService.getList(param)) :
                lessonService.getAll(param);
    }

//    @ApiOperation("点名")
//    @Log(description = "点名", type = "课次管理")
//    @PostMapping("/rollCall")
//    public JsonResponse rollCall(@Valid @RequestBody LessonSignSaveDTO dto, BindingResult result) {
//
//        CommonUtil.handleValidMessage(result);
//
//        SignStateEnum byCode = EnumTools.getByCode(dto.getState(), SignStateEnum.class);
//        if (byCode == null) {
//            return JsonResponseUtil.error("点名状态有误");
//        }
//        Long teacherId = UserAuthService.getCurrentUserId();
//        if (lessonService.rollCall(teacherId, dto.getLessonId(), dto.getStudentId(), byCode)) {
//            return JsonResponseUtil.success();
//        } else {
//            return JsonResponseUtil.error("点名失败");
//        }
//    }

//    @ApiOperation("批量点名")
//    @Log(description = "批量点名", type = "课次管理")
//    @PostMapping("/rollCallBatch")
//    public JsonResponse rollCallBatch(@Valid @RequestBody List<LessonSignSaveDTO> dtoList, BindingResult result) {
//
//        CommonUtil.handleValidMessage(result);
//
//        for (LessonSignSaveDTO item : dtoList) {
//            SignStateEnum byCode = EnumTools.getByCode(item.getState(), SignStateEnum.class);
//            if (byCode == null) {
//                return JsonResponseUtil.error("点名状态有误");
//            }
//        }
//        Long teacherId = UserAuthService.getCurrentUserId();
//        if (lessonService.rollCallBatch(teacherId, dtoList)) {
//            return JsonResponseUtil.success();
//        } else {
//            return JsonResponseUtil.error("点名失败");
//        }
//    }

    @ApiOperation("班级学员签到情况")
    @GetMapping("/classStudents")
    public List<ClassStudentSignVO> classStudents(
            @RequestParam(value = "lessonId", defaultValue = "") Long lessonId,
            @RequestParam(value = "unsigned", defaultValue = "") Boolean unsigned) {
        ClassStudentParamDTO param = new ClassStudentParamDTO();
        param.setLessonId(lessonId);
        param.setUnsigned(unsigned);
        return classStudentMapper.signRecord(param);
    }

    @ApiOperation("点名记录")
    @GetMapping("/rollCallRecord")
    public PaginationVO rollCallRecord(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize) {

        LessonStudentParamDTO param = new LessonStudentParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setTeacherId(UserAuthService.getCurrentUserId());

        return JsonResponseUtil.paginate(lessonStudentService.getList(param));
    }

    @ApiOperation("日课表数量统计")
    @GetMapping("/lessonCountEveryDay")
    public List<Map<String, Object>> lessonCountEveryDay(@RequestParam(value = "date", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        LessonParamDTO param = new LessonParamDTO();
        param.setStartDate(DateTool.firstDayOfMonth(date));
        param.setEndDate(DateTool.lastDayOfMonth(date));

        return tLessonService.getLessonNumEveryDay(param);
    }

    @ApiOperation("学生上课点评记录")
    @GetMapping("/students")
    public List<LessonStudentVO> students(@RequestParam(value = "lessonId") Long lessonId) {
        LessonStudentParamDTO param = new LessonStudentParamDTO();
        param.setLessonId(lessonId);
        return lessonStudentService.getAll(param);
    }

    @ApiOperation("点评记录")
    @GetMapping("/evaluateLog")
    public PaginationVO evaluateLog(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize) {
        LessonStudentParamDTO param = new LessonStudentParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setEvaluateTeacherId(UserAuthService.getCurrentUserId());
        return JsonResponseUtil.paginate(lessonStudentService.getList(param));
    }

    @ApiOperation("上课点评")
    @Log(description = "上课点评", type = "课次管理")
    @PostMapping("/evaluation")
    public JsonResponse evaluation(@Valid @RequestBody LessonEvaluateSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (lessonStudentService.evaluation(dto, UserAuthService.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }
}
