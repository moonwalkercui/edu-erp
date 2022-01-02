package com.hzb.erp.adminCenter.controller;


import com.hzb.erp.annotation.Log;
import com.hzb.erp.common.pojo.dto.StudentLeaveParamDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.StudentLeaveService;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 学员请假 前端控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/studentLeave")
@Api(value = "学员请假", tags = "学员请假")
public class StudentLeaveController {
    @Autowired
    private StudentLeaveService studentLeaveService;

    @ApiOperation("请假记录")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                             @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                             @RequestParam(value = "keyword", defaultValue = "") String studentName,
//                             @RequestParam(value = "state", defaultValue = "") String stateText,
                             @RequestParam(value = "teacherId", defaultValue = "") Long teacherId) {

        StudentLeaveParamDTO param = new StudentLeaveParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setTeacherId(teacherId);
        param.setStudentName(studentName);
        param.setStartDate(startDate);
        param.setEndDate(endDate);
//        VerifyStateEnum enumClass = EnumTool.getByDist(stateText, VerifyStateEnum.class);
//        if(enumClass!=null) {
//            param.setState(enumClass.getCode());
//        }

        return JsonResponseUtil.paginate(studentLeaveService.getList(param));
    }

//    @ApiOperation("请假审核通过")
//    @Log(description = "请假审核通过")
//    @PostMapping("/apply")
//    public JsonResponse apply(@RequestBody List<Long> ids ) {
//        if (studentLeaveService.handle(ids, VerifyStateEnum.PASS)) {
//            return JsonResponseUtils.success();
//        } else {
//            return JsonResponseUtils.error("未更新");
//        }
//    }
//
//    @ApiOperation("请假审核驳回")
//    @Log(description = "请假审核驳回")
//    @PostMapping("/reject")
//    public JsonResponse reject(@RequestBody List<Long> ids ) {
//        if (studentLeaveService.handle(ids, VerifyStateEnum.REJECT)) {
//            return JsonResponseUtils.success();
//        } else {
//            return JsonResponseUtils.error("未更新");
//        }
//    }//

    @ApiOperation("请假审核驳回")
    @Log(description = "请假审核驳回", type = "请假管理")
    @PostMapping("/cancel")
    public JsonResponse cancel(@RequestBody List<Long> ids) {
        if (studentLeaveService.cancel(ids, UserAuthService.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("未更新");
        }
    }

}
