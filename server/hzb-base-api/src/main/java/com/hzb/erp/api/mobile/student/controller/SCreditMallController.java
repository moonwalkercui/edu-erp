package com.hzb.erp.api.mobile.student.controller;

import com.hzb.erp.api.base.annotation.Log;
import com.hzb.erp.api.pc.creditMall.pojo.CreditExchangeParamDTO;
import com.hzb.erp.api.pc.creditMall.service.CreditExchangeService;
import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.api.pc.creditMall.pojo.CreditExchangeDTO;
import com.hzb.erp.api.pc.creditMall.service.CreditMallService;
import com.hzb.erp.api.mobile.student.service.StudentAuthService;
import com.hzb.erp.api.pc.student.entity.StudentCreditLog;
import com.hzb.erp.api.pc.student.pojo.StudentCreditLogParamDTO;
import com.hzb.erp.api.pc.student.service.StudentCreditLogService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Ryan 541720500@qq.com
 * 积分商城
 */
@RestController
@RequestMapping("/sCenter/credit")
@Api(value = "积分商城", tags = "积分商城")
public class SCreditMallController {
    @Autowired
    private CreditMallService creditMallService;
    @Autowired
    private CreditExchangeService creditExchangeService;
    @Autowired
    private StudentCreditLogService studentCreditLogService;

    @ApiOperation("积分礼品列表")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "keyword", defaultValue = "") String keyword) {
        return creditMallService.getAll(keyword);
    }

    @ApiOperation("积分礼品列表")
    @PostMapping("/addViewNum/{id}")
    public int list(@PathVariable(value = "id") Long id) {
        return creditMallService.addViewNum(id);
    }

    @ApiOperation("兑换礼品")
    @PostMapping("/exchange")
    @Log(description = "兑换礼品", type = "学生端", isStaff = false)
    @ResponseBody
    public JsonResponse exchange(@Valid @RequestBody CreditExchangeDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        Student student = StudentAuthService.getCurrentStudent();
        if(student == null) {
            return JsonResponseUtil.error("请登录");
        }
        dto.setUserId(student.getUserId());
        dto.setStudentId(student.getId());
        if (creditMallService.exchange(dto)) {
            return JsonResponseUtil.success("兑换成功");
        } else {
            return JsonResponseUtil.error();
        }
    }

    @ApiOperation("积分兑换记录")
    @GetMapping("/exchangeRecord")
    public Object exchangeRecord(CreditExchangeParamDTO param) {
        Student student = StudentAuthService.getCurrentStudent();
        if(student == null) {
            return JsonResponseUtil.error("请登录");
        }
        param.setStudentId(student.getId());
        return JsonResponseUtil.paginate(creditExchangeService.getList(param));
    }

    @ApiOperation("积分变更记录")
    @GetMapping("/changeRecord")
    public Object changeRecord(StudentCreditLogParamDTO param) {
        Student student = StudentAuthService.getCurrentStudent();
        if(student == null) {
            return JsonResponseUtil.error("请登录");
        }
        param.setStudentId(student.getId());
        return JsonResponseUtil.paginate(studentCreditLogService.getList(param));
    }
}
