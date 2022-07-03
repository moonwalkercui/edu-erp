package com.hzb.erp.studentMobile.controller;

import com.hzb.erp.base.annotation.Log;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.pojo.dto.CreditExchangeDTO;
import com.hzb.erp.common.service.CreditMallService;
import com.hzb.erp.studentMobile.service.StudentAuthService;
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
}
