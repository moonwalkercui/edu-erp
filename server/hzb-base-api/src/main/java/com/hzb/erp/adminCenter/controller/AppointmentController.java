package com.hzb.erp.adminCenter.controller;


import com.hzb.erp.annotation.Log;
import com.hzb.erp.common.enums.StudentStageEnum;
import com.hzb.erp.common.pojo.dto.AppointmentParamDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.AppointmentService;
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

    @ApiOperation("审核通过")
    @Log(description = "审核通过", type = "预约管理")
    @PostMapping("/pass")
    public JsonResponse pass(@RequestBody List<Long> ids) {
        if (appointmentService.handleAudit(ids, true, UserAuthUtil.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }


    @ApiOperation("审核驳回")
    @Log(description = "审核驳回", type = "预约管理")
    @PostMapping("/reject")
    public JsonResponse reject(@RequestBody List<Long> ids) {
        if (appointmentService.handleAudit(ids, false, UserAuthUtil.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

}
