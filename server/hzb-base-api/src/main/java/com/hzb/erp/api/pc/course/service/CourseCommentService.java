package com.hzb.erp.api.pc.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.course.entity.CourseComment;
import com.hzb.erp.api.pc.course.pojo.CourseCommentParamDTO;
import com.hzb.erp.api.pc.shop.pojo.OrderEvaluateDTO;
import com.hzb.erp.api.pc.course.pojo.CourseCommentVO;

/**
 * <p>
 * 课程评价 服务类
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface CourseCommentService extends IService<CourseComment> {
    IPage<CourseCommentVO> getList(CourseCommentParamDTO param);

    /**
    * 订单评价
    * */
    void createByOrder(OrderEvaluateDTO dto);
}
