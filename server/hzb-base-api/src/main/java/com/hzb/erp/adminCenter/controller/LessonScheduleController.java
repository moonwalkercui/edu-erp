package com.hzb.erp.adminCenter.controller;


import com.hzb.erp.base.annotation.Log;
import com.hzb.erp.base.annotation.PreventMultiSubmit;
import com.hzb.erp.common.entity.LessonSchedule;
import com.hzb.erp.common.entity.LessonScheduleSetting;
import com.hzb.erp.common.enums.LessonTypeEnum;
import com.hzb.erp.common.pojo.dto.LessonScheduleParamDTO;
import com.hzb.erp.common.pojo.dto.lessonSchedule.ScheduleSaveDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.LessonScheduleService;
import com.hzb.erp.common.service.LessonScheduleSettingService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.EnumTools;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 排课计划 前端控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/lessonSchedule")
@Api(value = "排课计划", tags = "排课计划")
public class LessonScheduleController {

    @Autowired
    private LessonScheduleService lessonScheduleService;
    @Autowired
    private LessonScheduleSettingService lessonScheduleSettingService;

    @ApiOperation("排课计划")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "1") Integer page, // 若为null则是查全部
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "classId", defaultValue = "") Long classId,
                             @RequestParam(value = "lessonType", defaultValue = "") String lessonType) {
        LessonScheduleParamDTO param = new LessonScheduleParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setClassId(classId);

        LessonTypeEnum type = EnumTools.getByDist(lessonType, LessonTypeEnum.class);
        if (type != null) {
            param.setLessonType(type.getCode());
        }

        return JsonResponseUtil.paginate(lessonScheduleService.getList(param));
    }

    @ApiOperation("创建和修改排课计划")
    @Log(description = "创建和修改排课计划", type = "排课管理")
    @PostMapping("/saveSchedule")
    @PreventMultiSubmit
    public JsonResponse saveSchedule(@Valid @RequestBody ScheduleSaveDTO scheduleSaveDTO, BindingResult result) {

        if (scheduleSaveDTO.getEndDate().isBefore(scheduleSaveDTO.getStartDate())) {
            return JsonResponseUtil.error("日期设置错误");
        }

        CommonUtil.handleValidMessage(result);
        LessonSchedule schedule = lessonScheduleService.saveOrUpdate(scheduleSaveDTO);
        Integer count = lessonScheduleService.checkConflict(Collections.singletonList(schedule.getId()));

        if (count > 0) {
            return JsonResponseUtil.success("提交成功, 但检测到" + count + "个冲突!");
        } else {
            return JsonResponseUtil.success("提交成功, 可生成课时.");
        }
    }

    @ApiOperation("生成课表")
    @Log(description = "生成课表", type = "排课管理")
    @PostMapping("/generateLesson")
    @PreventMultiSubmit
    public JsonResponse generateLesson(@RequestBody List<Long> ids) {
        Integer count = lessonScheduleService.generateLesson(ids);
        return count > 0
                ? JsonResponseUtil.success("已生成" + count + "次课,请到课表里查看")
                : JsonResponseUtil.error("未生成课表");
    }

    @ApiOperation("删除排课计划")
    @Log(description = "删除排课计划", type = "排课管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        if (lessonScheduleService.removeByIds(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("排课计划冲突检查")
    @Log(description = "排课计划冲突检查", type = "排课管理")
    @PostMapping("/checkConflict")
    public JsonResponse checkConflict(@RequestBody List<Long> ids) {
        Integer count = lessonScheduleService.checkConflict(ids);
        if (count > 0) {
            return JsonResponseUtil.error("检测到" + count + "个冲突,请处理.");
        } else {
            return JsonResponseUtil.success();
        }
    }

    @ApiOperation("加载上课时间")
    @GetMapping("/lessonTimes")
    public List<LessonScheduleSetting> lessonTimes(@RequestParam(value = "id") Long scheduleId) {
        List<LessonScheduleSetting> list = lessonScheduleSettingService.getListByScheduleId(scheduleId);
        return list;
    }


}
