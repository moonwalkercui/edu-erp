package com.hzb.erp.adminCenter.controller;

import com.hzb.erp.base.annotation.Log;
import com.hzb.erp.common.enums.OrderRefundStateEnum;
import com.hzb.erp.common.enums.OrderStateEnum;
import com.hzb.erp.common.pojo.dto.IdsAndContentDTO;
import com.hzb.erp.common.pojo.dto.OrderListParamDTO;
import com.hzb.erp.common.pojo.dto.OrderRefundParamDTO;
import com.hzb.erp.common.service.OrderRefundService;
import com.hzb.erp.common.service.OrderService;
import com.hzb.erp.security.Util.UserAuthUtil;
import com.hzb.erp.utils.EnumTools;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class ShopController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRefundService orderRefundService;

    @ApiOperation("订单列表")
    @GetMapping("/orderList")
    public Object orderList(@RequestParam(value = "page", defaultValue = "") Integer page,
                            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                            @RequestParam(value = "sn", defaultValue = "") String sn,
                            @RequestParam(value = "studentId", defaultValue = "") Long studentId,
                            @RequestParam(value = "courseId", defaultValue = "") Long courseId,
                            @RequestParam(value = "state", defaultValue = "") String[] state) {
        OrderListParamDTO param = new OrderListParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setSn(sn);
        param.setStudentId(studentId);
        param.setCourseId(courseId);
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
                            @RequestParam(value = "sn", defaultValue = "") String refundSn,
                            @RequestParam(value = "studentId", defaultValue = "") Long studentId,
                            @RequestParam(value = "orderSn", defaultValue = "") String orderSn,
                            @RequestParam(value = "state", defaultValue = "") String[] state) {
        OrderRefundParamDTO param = new OrderRefundParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setRefundSn(refundSn);
        param.setStudentId(studentId);
        param.setOrderSn(orderSn);
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


    @ApiOperation("退款审核通过")
    @Log(description = "退款审核通过", type = "退款管理")
    @PostMapping("/refundApprove")
    public Object refundApprove(@RequestBody IdsAndContentDTO dto) {
//        if (courseService.open(ids)) {
//            return JsonResponseUtil.success();
//        } else {
//            return JsonResponseUtil.error("操作失败");
//        }
        orderRefundService.handleApprove(dto, UserAuthUtil.getCurrentUserId());
        return null;
    }

    @ApiOperation("退款审核驳回")
    @Log(description = "退款审核驳回", type = "退款管理")
    @PostMapping("/refundReject")
    public Object refundReject(@RequestBody IdsAndContentDTO dto) {
//        if (courseService.open(ids)) {
//            return JsonResponseUtil.success();
//        } else {
//            return JsonResponseUtil.error("操作失败");
//        }
        orderRefundService.handleReject(dto, UserAuthUtil.getCurrentUserId());
        return null;
    }


}
