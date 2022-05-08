package com.hzb.erp.adminCenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzb.erp.base.annotation.Log;
import com.hzb.erp.common.entity.Holiday;
import com.hzb.erp.common.service.HolidayService;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 节假日 前端控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/holiday")
@Api(value = "节假日", tags = "节假日")
public class HolidayController {
    @Autowired
    private HolidayService holidayService;

    @ApiOperation("假期列表")
    @GetMapping("/list")
    public List<Holiday> list() {
        return holidayService.list();
    }

    @ApiOperation("添加假期")
    @Log(description = "添加假期", type = "系统管理")
    @PostMapping("/add/{date}")
    public JsonResponse add(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        QueryWrapper<Holiday> qw = new QueryWrapper<>();
        qw.eq("date", date.toString()).last("limit 1");
        Holiday find = holidayService.getOne(qw);
        if (find != null) {
            return delete(date);
        }
        Holiday day = new Holiday();
        day.setDate(date);
        return holidayService.save(day) ? JsonResponseUtil.success("已添加") : JsonResponseUtil.error("操作失败");
    }

    @ApiOperation("删除假期")
    @Log(description = "删除假期", type = "系统管理")
    @PostMapping("/delete/{date}")
    public JsonResponse delete(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        QueryWrapper<Holiday> qw = new QueryWrapper<>();
        qw.eq("date", date.toString());
        return holidayService.remove(qw) ? JsonResponseUtil.success("已删除") : JsonResponseUtil.error("操作失败");
    }

}
