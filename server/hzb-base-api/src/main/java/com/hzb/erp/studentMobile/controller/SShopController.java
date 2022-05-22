package com.hzb.erp.studentMobile.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzb.erp.common.entity.CourseImage;
import com.hzb.erp.common.entity.CourseSection;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.entity.Subject;
import com.hzb.erp.common.mapper.CourseCommentMapper;
import com.hzb.erp.common.mapper.CourseImageMapper;
import com.hzb.erp.common.mapper.CourseSectionMapper;
import com.hzb.erp.common.pojo.dto.CourseCommentParamDTO;
import com.hzb.erp.common.pojo.dto.CourseParamDTO;
import com.hzb.erp.common.pojo.vo.CourseSectionVO;
import com.hzb.erp.common.pojo.vo.CourseVO;
import com.hzb.erp.common.service.CourseCommentService;
import com.hzb.erp.common.service.CourseService;
import com.hzb.erp.common.service.SubjectService;
import com.hzb.erp.common.pojo.dto.OrderConfirmDTO;
import com.hzb.erp.studentMobile.pojo.vo.CourseInfoVO;
import com.hzb.erp.studentMobile.service.StudentAuthService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 在线购课控制器
 *
 * @author Ryan 541720500@qq.com
 */
@RestController
@RequestMapping("/sCenter/shop")
@Api(value = "在线购课控制器", tags = "在线购课")
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

    @ApiOperation("购课课程列表")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "") Integer page,
                       @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
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

        // 介绍图片
        QueryWrapper<CourseImage> qw2 = new QueryWrapper<>();
        qw2.eq("course_id", id);
        info.setImageList(courseImageMapper.selectList(qw2));

        return JsonResponseUtil.data(info);
    }

    @ApiOperation("评价列表")
    @GetMapping("/commentList")
    public Object commentList(@RequestParam(value = "page", defaultValue = "") Integer page,
                              @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                              @RequestParam(value = "courseId", defaultValue = "") Long courseId) {
        CourseCommentParamDTO param = new CourseCommentParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setCourseId(courseId);
        param.setState(true);
        return JsonResponseUtil.paginate(courseCommentService.getList(param));
    }

    @ApiOperation("订单确认并支付")
    @GetMapping("/orderConfirm")
    public Object orderConfirm(@Valid @RequestBody OrderConfirmDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);

        return null;
    }

}
