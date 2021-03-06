package com.hzb.erp.api.pc.lesson.controller;


import com.hzb.erp.api.base.annotation.Log;
import com.hzb.erp.api.pc.lesson.pojo.AppointmentParamDTO;
import com.hzb.erp.common.pojo.PaginationVO;
import com.hzb.erp.api.pc.lesson.service.AppointmentService;
import com.hzb.erp.security.Util.UserAuthUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 试听预约记录 前端控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/appointment")
@Api(value = "试听预约记录", tags = "试听预约记录")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @ApiOperation("预约列表/试听申请记录")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "studentId", defaultValue = "") Long studentId) {
        AppointmentParamDTO param = new AppointmentParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setStudentId(studentId);
        return JsonResponseUtil.paginate(appointmentService.getList(param));
    }

    @ApiOperation("确认预约")
    @Log(description = "确认预约", type = "预约管理")
    @PostMapping("/approve")
    public JsonResponse approve(@RequestBody List<Long> ids) {
        if (appointmentService.handleAudit(ids, true, UserAuthUtil.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("取消预约")
    @Log(description = "取消预约", type = "预约管理")
    @PostMapping("/cancel")
    public JsonResponse cancel(@RequestBody List<Long> ids) {
        if (appointmentService.handleAudit(ids, false, UserAuthUtil.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

}
