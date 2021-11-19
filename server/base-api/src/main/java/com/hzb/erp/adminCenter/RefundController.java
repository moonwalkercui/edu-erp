package com.hzb.erp.adminCenter;


import com.hzb.erp.annotation.Log;
import com.hzb.erp.annotation.PreventMultiSubmit;
import com.hzb.erp.common.pojo.dto.RefundSaveDTO;
import com.hzb.erp.common.service.RefundService;
import com.hzb.erp.common.service.StudentCourseService;
import com.hzb.erp.common.service.StudentLessonCountLogService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 学员退款记录 前端控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/refund")
@Api(value = "学员退款管理", tags = "学员退款管理")
public class RefundController {

    @Autowired
    private RefundService refundService;
    @Autowired
    private StudentCourseService studentCourseService;
    @Autowired
    private StudentLessonCountLogService studentLessonCountLogService;

    @ApiOperation("发起退款")
    @Log(description = "发起退款", type = "退款管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse saveStudent(@Valid @RequestBody RefundSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (refundService.saveOrUpdateByDTO(dto, UserAuthService.getCurrentUserId())) {
            studentLessonCountLogService.addOneByRefund(dto.getStudentId(), dto.getStudentCourseId(), dto.getRefundLessonCount(), UserAuthService.getCurrentUserId());
            return JsonResponseUtil.success("发起退款成功,等待财务审核");
        } else {
            return JsonResponseUtil.error("退费失败");
        }
    }

}
