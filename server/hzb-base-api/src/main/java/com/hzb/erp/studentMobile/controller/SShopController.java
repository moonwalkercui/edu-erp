package com.hzb.erp.studentMobile.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.hzb.erp.base.annotation.Log;
import com.hzb.erp.common.entity.*;
import com.hzb.erp.common.enums.OrderStateEnum;
import com.hzb.erp.common.mapper.CourseCommentMapper;
import com.hzb.erp.common.mapper.CourseImageMapper;
import com.hzb.erp.common.mapper.CourseSectionMapper;
import com.hzb.erp.common.mapper.UserMapper;
import com.hzb.erp.common.pojo.dto.*;
import com.hzb.erp.common.pojo.vo.CourseSectionVO;
import com.hzb.erp.common.pojo.vo.CourseVO;
import com.hzb.erp.common.pojo.vo.OrderVO;
import com.hzb.erp.common.service.*;
import com.hzb.erp.studentMobile.pojo.vo.CourseInfoVO;
import com.hzb.erp.studentMobile.service.StudentAuthService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.wechat.service.WxPaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 在线购课控制器
 *
 * @author Ryan 541720500@qq.com
 */
@RestController
@RequestMapping("/sCenter/shop")
@Api(value = "在线购课控制器", tags = "在线购课")
@Slf4j
public class SShopController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private CourseCommentService courseCommentService;
    @Autowired
    private CourseSectionMapper courseSectionMapper;
    @Autowired
    private CourseCommentMapper courseCommentMapper;
    @Autowired
    private CourseImageMapper courseImageMapper;
    @Autowired
    private WxPayService wxService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRefundService orderRefundService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WxPaymentService wxPaymentService;

    @ApiOperation("购课课程列表")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "") Integer page,
                       @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                       @RequestParam(value = "recommend", defaultValue = "") Boolean recommend,
                       @RequestParam(value = "subjectId", defaultValue = "") Long subjectId) {
        CourseParamDTO param = new CourseParamDTO();
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setSubjectId(subjectId);
        param.setRecommend(recommend);
        param.setForSale(true);
        param.setState(Collections.singletonList(1));
        param.setWithFavRate(true);
        return JsonResponseUtil.paginate(courseService.getList(param));
    }

    @ApiOperation("科目列表")
    @GetMapping("/subjectlist")
    public Object subjectlist() {
        QueryWrapper<Subject> qw = new QueryWrapper<>();
        qw.orderByDesc("sort_num");
        qw.orderByDesc("id");
        return JsonResponseUtil.data(subjectService.list(qw));
    }

    @ApiOperation("课程信息")
    @GetMapping("/courseInfo")
    public Object courseInfo(@RequestParam(value = "id") Long id) {
        CourseInfoVO info = new CourseInfoVO();
        CourseVO course = courseService.getInfo(id);
        BeanUtils.copyProperties(course, info);
        // 大纲
        QueryWrapper<CourseSection> qw1 = new QueryWrapper<>();
        qw1.eq("course_id", id);
        List<CourseSection> sections = courseSectionMapper.selectList(qw1);
        List<CourseSectionVO> courseSectionVOS = new ArrayList<>();
        if (sections != null && sections.size() > 0) {
            for (CourseSection sect : sections) {
                CourseSectionVO vo = new CourseSectionVO();
                BeanUtils.copyProperties(sect, vo);
                courseSectionVOS.add(vo);
            }
        }
        info.setSectionList(courseSectionVOS);

        // 评价
        CourseCommentParamDTO paramDTO = new CourseCommentParamDTO();
        paramDTO.setCourseId(id);
        paramDTO.setState(true);
        paramDTO.setLimit(2);
        info.setCommentList(courseCommentMapper.getList(paramDTO));

        Double favRate = courseCommentMapper.getFavRate(id);
        info.setFavRate( favRate == null ? 0 : (double) Math.round(favRate * 100) / 100);

        // 介绍图片
        QueryWrapper<CourseImage> qw2 = new QueryWrapper<>();
        qw2.eq("course_id", id);
        info.setImageList(courseImageMapper.selectList(qw2));

        return JsonResponseUtil.data(info);
    }

    @ApiOperation("评价列表")
    @GetMapping("/commentList")
    public Object commentList(@RequestParam(value = "page", defaultValue = "") Integer page,
                              @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                              @RequestParam(value = "courseId", defaultValue = "") Long courseId) {
        CourseCommentParamDTO param = new CourseCommentParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setCourseId(courseId);
        param.setState(true);
        return JsonResponseUtil.paginate(courseCommentService.getList(param));
    }

    @ApiOperation("订单确认返回订单支付信息")
    @PostMapping("/orderConfirm")
    public Object orderConfirm(@Valid @RequestBody OrderConfirmDTO dto, BindingResult result) throws WxPayException {
        CommonUtil.handleValidMessage(result);
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return JsonResponseUtil.error("请先添加学生");
        }
        dto.setUserId(student.getUserId());
        if(!dto.getStudentId().equals(student.getId())) {
            return JsonResponseUtil.error("学生账号异常，请到首页切换学生");
        }
        dto.setStudentId(student.getId());
        return orderService.makeOrder(dto);
    }

    @ApiOperation(value = "统一下单组装所需支付参数")
    @PostMapping("/createOrder")
    public Object createOrder(@RequestBody Order order) {
        Long uid = StudentAuthService.getCurrentUserId();
        String openid = userMapper.getWxOpenid(uid);
        WxPayUnifiedOrderRequest orderRequestParam = wxPaymentService.buildPayParamByOrder(order, openid,"JSAPI");
        log.info("=============支付提交对象实体==============");
        log.info(orderRequestParam.toString());
        Object res;
        try{
            res = this.wxService.createOrder(orderRequestParam);
            log.info("=================支付参数:==============");
            log.info(res.toString());
        } catch (WxPayException e) {
            return JsonResponseUtil.error(e.getMessage());
        }
        return res;
    }

    @ApiOperation("订单列表")
    @GetMapping("/orderList")
    public Object orderList(@RequestParam(value = "page", defaultValue = "") Integer page,
                            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                            @RequestParam(value = "state", defaultValue = "") String[] state) {
        OrderListParamDTO param = new OrderListParamDTO();
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setStudentId(student.getId());
        param.setState(state == null ? null : Arrays.stream(state).map(Integer::valueOf).collect(Collectors.toList()));
        return JsonResponseUtil.paginate(orderService.getList(param));
    }

    @ApiOperation("订单详情")
    @GetMapping("/orderInfo/{orderId}")
    public OrderVO orderInfo(@PathVariable("orderId") Long orderId) {
        return orderService.getInfo(orderId);
    }

    @ApiOperation("获取未评价数")
    @GetMapping("/orderCountUnevaluate")
    public Long orderCountUnevaluate() {
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return 0L;
        }
        QueryWrapper<Order> qw = new QueryWrapper<>();
        qw.eq("state", OrderStateEnum.PAID.getCode());
        qw.eq("student_id", student.getId());
        return orderService.count(qw);
    }

    @ApiOperation("订单评价")
    @Log(description = "订单评价", type = "学生端", isStaff = false)
    @PostMapping("/orderEvaluate")
    public Object orderEvaluate(@Valid @RequestBody OrderEvaluateDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return JsonResponseUtil.error("请先添加学生");
        }
        dto.setStudentId(student.getUserId());
        courseCommentService.createByOrder(dto);
        return JsonResponseUtil.success("已评价");
    }

    @ApiOperation(value = "申请退款")
    @Log(description = "申请退款", type = "学生端", isStaff = false)
    @PostMapping("/orderRefund")
    public Object orderRefund(@Valid @RequestBody OrderRefundDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return JsonResponseUtil.error("请先添加学生");
        }
        dto.setUserId(student.getUserId());
        dto.setStudentId(student.getId());
        orderRefundService.handleRefund(dto);
        return JsonResponseUtil.success("退款申请已提交");
    }

    @ApiOperation("取消订单")
    @Log(description = "取消订单", type = "学生端", isStaff = false)
    @PostMapping("/orderCancel/{orderId}")
    public Object orderCancel(@PathVariable("orderId") Long orderId) {
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return JsonResponseUtil.error("请先添加学生");
        }
        QueryWrapper<Order> qw = new QueryWrapper<>();
        qw.eq("id", orderId).eq("student_id", student.getId());
        Order order = orderService.getOne(qw);
        if (orderService.cancel(order)) {
            return JsonResponseUtil.success("已取消");
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("删除订单")
    @Log(description = "删除订单", type = "学生端", isStaff = false)
    @PostMapping("/orderDelete/{orderId}")
    public Object orderDelete(@PathVariable("orderId") Long orderId) {
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return JsonResponseUtil.error("请先添加学生");
        }

        QueryWrapper<Order> qw = new QueryWrapper<>();
        qw.eq("id", orderId).eq("student_id", student.getId());

        if (orderService.remove(qw)) {
            return JsonResponseUtil.success("已删除");
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }


}
