package com.hzb.erp.api.pc.shop.controller;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.hzb.erp.api.base.annotation.Log;
import com.hzb.erp.api.pc.course.pojo.CourseCommentParamDTO;
import com.hzb.erp.api.pc.shop.pojo.OrderListParamDTO;
import com.hzb.erp.api.pc.shop.pojo.OrderRefundParamDTO;
import com.hzb.erp.configuration.SystemConfig;
import com.hzb.erp.api.pc.course.entity.CourseComment;
import com.hzb.erp.api.pc.shop.entity.OrderRefund;
import com.hzb.erp.common.enums.OrderRefundStateEnum;
import com.hzb.erp.common.enums.OrderStateEnum;
import com.hzb.erp.exception.BizException;
import com.hzb.erp.api.pc.course.service.CourseCommentService;
import com.hzb.erp.api.pc.shop.service.OrderRefundService;
import com.hzb.erp.api.pc.shop.service.OrderService;
import com.hzb.erp.utils.EnumTools;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.wechat.entiry.WxOrderRefund;
import com.hzb.erp.wechat.service.WxPaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.github.binarywang.wxpay.service.WxPayService;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 在线购课控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/shop")
@Api(value = "在线购课管理", tags = "在线购课管理")
@Slf4j
public class ShopController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRefundService orderRefundService;

    @Autowired
    private CourseCommentService courseCommentService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private WxPaymentService wxPaymentService;

    @Resource
    private SystemConfig systemConfig;

    @ApiOperation("订单列表")
    @GetMapping("/orderList")
    public Object orderList(@RequestParam(value = "page", defaultValue = "") Integer page,
                            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                            @RequestParam(value = "sn", defaultValue = "") String sn,
                            @RequestParam(value = "studentId", defaultValue = "") Long studentId,
                            @RequestParam(value = "courseId", defaultValue = "") Long courseId,
                            @RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                            @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                            @RequestParam(value = "state", defaultValue = "") String[] state) {
        OrderListParamDTO param = new OrderListParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setSn(sn);
        param.setStudentId(studentId);
        param.setCourseId(courseId);
        param.setStartDate(startDate);
        param.setEndDate(endDate);
        List<Integer> value = new ArrayList<>();
        for (String s : state) {
            OrderStateEnum enumClass = EnumTools.getByDist(s, OrderStateEnum.class);
            if (enumClass != null) {
                value.add(enumClass.getCode());
            }
        }
        param.setState(value);
        return JsonResponseUtil.paginate(orderService.getList(param));
    }


    @ApiOperation("退款列表")
    @GetMapping("/refundList")
    public Object refundList(@RequestParam(value = "page", defaultValue = "") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "refundSn", defaultValue = "") String refundSn,
                             @RequestParam(value = "orderSn", defaultValue = "") String orderSn,
                             @RequestParam(value = "studentId", defaultValue = "") Long studentId,
                             @RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                             @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                             @RequestParam(value = "state", defaultValue = "") String[] state) {
        OrderRefundParamDTO param = new OrderRefundParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setRefundSn(refundSn);
        param.setStudentId(studentId);
        param.setOrderSn(orderSn);
        param.setStartDate(startDate);
        param.setEndDate(endDate);
        List<Integer> value = new ArrayList<>();
        for (String s : state) {
            OrderRefundStateEnum enumClass = EnumTools.getByDist(s, OrderRefundStateEnum.class);
            if (enumClass != null) {
                value.add(enumClass.getCode());
            }
        }
        param.setState(value);
        return JsonResponseUtil.paginate(orderRefundService.getList(param));
    }

    @ApiOperation("执行退款")
    @Log(description = "执行退款", type = "运营管理")
    @PostMapping("/executeRefund")
    public Object executeRefund(@RequestBody List<Long> ids) {

        if (!systemConfig.getIsDemo()) {
            return JsonResponseUtil.error("DEMO版此处不能操作"); // todo 发行
        }

        List<OrderRefund> list = orderRefundService.listByIds(ids);
        for (OrderRefund item : list) {
            try {
                if (!(OrderRefundStateEnum.PASS.equals(item.getState())) || OrderRefundStateEnum.FAIL.equals(item.getState())) {
                    throw new BizException("未经财务审核的微信退款无法发起");
                }
                WxOrderRefund wxOrderRefund = new WxOrderRefund();
                BeanUtils.copyProperties(item, wxOrderRefund);
                wxPayService.refund(wxPaymentService.buildRefundParamByOrderRefund(wxOrderRefund));
            } catch ( WxPayException e) {
                item.setState(OrderRefundStateEnum.FAIL);
                orderRefundService.updateById(item);
                log.error("【微信退款异常】" + e.getMessage());
                throw new BizException("【微信退款异常】" + e.getMessage());
            }
        }
        return JsonResponseUtil.success("微信退款已发起，结果请关注微信账户平台");
    }


    @ApiOperation("订单评价列表")
    @GetMapping("/orderCommentList")
    public Object orderCommentList(@RequestParam(value = "page", defaultValue = "") Integer page,
                                   @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                                   @RequestParam(value = "orderSn", defaultValue = "") String orderSn,
                                   @RequestParam(value = "studentId", defaultValue = "") Long studentId,
                                   @RequestParam(value = "studentId", defaultValue = "") Long courseId,
                                   @RequestParam(value = "state", defaultValue = "") Boolean state) {
        CourseCommentParamDTO param = new CourseCommentParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setCourseId(courseId);
        param.setStudentId(studentId);
        param.setOrderSn(orderSn);
        param.setState(state);
        return JsonResponseUtil.paginate(courseCommentService.getList(param));
    }

    @ApiOperation("批量显示课程评价")
    @Log(description = "批量显示课程评价", type = "运营管理")
    @PostMapping("/courseCommentShow")
    public Object courseCommentShow(@RequestBody List<Long> ids) {
        List<CourseComment> list = courseCommentService.listByIds(ids);
        for (CourseComment item : list) {
            item.setState(true);
        }
        courseCommentService.updateBatchById(list);
        return JsonResponseUtil.success();
    }

    @ApiOperation("批量隐藏课程评价")
    @Log(description = "批量隐藏课程评价", type = "运营管理")
    @PostMapping("/courseCommentHide")
    public Object courseCommentHide(@RequestBody List<Long> ids) {
        List<CourseComment> list = courseCommentService.listByIds(ids);
        for (CourseComment item : list) {
            item.setState(false);
        }
        courseCommentService.updateBatchById(list);
        return JsonResponseUtil.success();
    }

    @ApiOperation("批量删除课程评价")
    @Log(description = "批量删除课程评价", type = "运营管理")
    @PostMapping("/courseCommentDel")
    public Object courseCommentDel(@RequestBody List<Long> ids) {
        courseCommentService.removeByIds(ids);
        return JsonResponseUtil.success();
    }

}
