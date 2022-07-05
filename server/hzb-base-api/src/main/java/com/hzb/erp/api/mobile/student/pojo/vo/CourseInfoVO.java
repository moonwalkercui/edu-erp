package com.hzb.erp.api.mobile.student.pojo.vo;

import com.hzb.erp.api.pc.course.entity.CourseImage;
import com.hzb.erp.api.pc.course.pojo.CourseCommentVO;
import com.hzb.erp.api.pc.course.pojo.CourseSectionVO;
import com.hzb.erp.api.pc.course.pojo.CourseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p> 课程信息VO </p>
 *
 * @author Ryan 541720500@qq.com
 */
@Data
public class CourseInfoVO extends CourseVO {

    @ApiModelProperty(value = "章节列表")
    private List<CourseSectionVO> sectionList;

    @ApiModelProperty(value = "评价列表")
    private List<CourseCommentVO> commentList;

    @ApiModelProperty(value = "课程介绍图片列表")
    private List<CourseImage> imageList;
}
