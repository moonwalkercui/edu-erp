package com.hzb.erp.api.pc.course.controller;

import com.hzb.erp.api.base.annotation.Log;
import com.hzb.erp.api.base.annotation.PreventMultiSubmit;
import com.hzb.erp.api.pc.course.pojo.CourseTrialParamDTO;
import com.hzb.erp.api.pc.course.pojo.CourseTrialRecordParamDTO;
import com.hzb.erp.api.pc.course.pojo.CourseTrialSaveDTO;
import com.hzb.erp.common.pojo.PaginationVO;
import com.hzb.erp.api.pc.course.service.CourseTrialRecordService;
import com.hzb.erp.api.pc.course.service.CourseTrialService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 课程体验卡控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/trial")
@Api(value = "课程体验卡管理", tags = "课程体验卡管理")
@Slf4j
public class CourseTrailController {

    @Autowired
    private CourseTrialService courseTrialService;

    @Autowired
    private CourseTrialRecordService courseTrialRecordService;

    @ApiOperation("体验卡列表")
    @GetMapping("/trialList")
    public Object trialList(@RequestParam(value = "page", defaultValue = "") Integer page,
                            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                            @RequestParam(value = "title", defaultValue = "") String title,
                            @RequestParam(value = "courseId", defaultValue = "") Long courseId,
                            @RequestParam(value = "state", defaultValue = "") Boolean state) {
        CourseTrialParamDTO param = new CourseTrialParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setCourseId(courseId);
        param.setTitle(title);
        param.setState(state);
        return JsonResponseUtil.paginate(courseTrialService.getList(param));
    }

    @ApiOperation("创建和修改体验卡")
    @Log(description = "创建和修改体验卡", type = "体验卡管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody CourseTrialSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        courseTrialService.saveOrUpdateByDTO(dto);
        return JsonResponseUtil.success();
    }

    @ApiOperation("删除体验卡")
    @Log(description = "删除体验卡", type = "体验卡管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        if (courseTrialService.removeByIds(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

    @ApiOperation("体验卡领取列表")
    @GetMapping("/recordsList")
    public PaginationVO recordsList(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
            @RequestParam(value = "trialId", defaultValue = "") Long trialId,
            @RequestParam(value = "studentId", defaultValue = "") Long studentId) {

        CourseTrialRecordParamDTO param = new CourseTrialRecordParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setTrialId(trialId);
        param.setStudentId(studentId);

        return JsonResponseUtil.paginate(courseTrialRecordService.getList(param));
    }

}
