package com.hzb.erp.studentMobile.controller;

import com.hzb.erp.common.service.CreditMallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Object list() {
        return creditMallService.getAll();
    }

}
