package com.hzb.erp.adminCenter.controller;


import com.hzb.erp.common.pojo.dto.AppointmentParamDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.AppointmentService;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
