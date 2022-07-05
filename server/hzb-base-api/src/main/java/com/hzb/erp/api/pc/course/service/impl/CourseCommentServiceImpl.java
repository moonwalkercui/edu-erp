package com.hzb.erp.api.pc.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.pc.course.entity.CourseComment;
import com.hzb.erp.api.pc.shop.entity.Order;
import com.hzb.erp.api.pc.shop.entity.OrderItem;
import com.hzb.erp.common.enums.OrderItemTypeEnum;
import com.hzb.erp.common.enums.OrderStateEnum;
import com.hzb.erp.exception.BizException;
import com.hzb.erp.api.pc.course.mapper.CourseCommentMapper;
import com.hzb.erp.api.pc.shop.mapper.OrderItemMapper;
import com.hzb.erp.api.pc.shop.mapper.OrderMapper;
import com.hzb.erp.api.pc.course.pojo.CourseCommentParamDTO;
import com.hzb.erp.api.pc.shop.pojo.OrderEvaluateDTO;
import com.hzb.erp.api.pc.course.pojo.CourseCommentVO;
import com.hzb.erp.api.pc.course.service.CourseCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程评论 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class CourseCommentServiceImpl extends ServiceImpl<CourseCommentMapper, CourseComment> implements CourseCommentService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public IPage<CourseCommentVO> getList(CourseCommentParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public void createByOrder(OrderEvaluateDTO dto) {
        Order order = orderMapper.selectById(dto.getOrderId());
        if(!OrderStateEnum.PAID.equals(order.getState())) {
            throw new BizException("仅已付款的订单可评价");
        }

        QueryWrapper<OrderItem> qw = new QueryWrapper<>();
        qw.eq("item_type", OrderItemTypeEnum.COURSE.getCode());
        List<OrderItem> courseList = orderItemMapper.selectList(qw);

        for(OrderItem oi: courseList) {
            CourseComment cc = new CourseComment();
            cc.setCourseId(oi.getItemId());
            cc.setStudentId(dto.getStudentId());
            cc.setOrderId(dto.getOrderId());
            cc.setContent(dto.getContent());
            cc.setScore(dto.getScore());
            cc.setState(false);
            this.baseMapper.insert(cc);
        }

        order.setState(OrderStateEnum.EVALUATE);
        orderMapper.updateById(order);
    }
}
