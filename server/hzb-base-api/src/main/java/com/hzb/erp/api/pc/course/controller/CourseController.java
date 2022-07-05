package com.hzb.erp.api.pc.course.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.base.annotation.Log;
import com.hzb.erp.api.base.annotation.PreventMultiSubmit;
import com.hzb.erp.api.pc.course.entity.CourseImage;
import com.hzb.erp.api.pc.course.entity.CourseSection;
import com.hzb.erp.common.enums.SwitchEnum;
import com.hzb.erp.api.pc.course.mapper.CourseImageMapper;
import com.hzb.erp.api.pc.course.mapper.CourseSectionMapper;
import com.hzb.erp.api.pc.course.pojo.CourseParamDTO;
import com.hzb.erp.api.pc.course.pojo.CourseSaveDTO;
import com.hzb.erp.api.pc.course.pojo.CourseSectionDTO;
import com.hzb.erp.api.pc.course.pojo.CourseVO;
import com.hzb.erp.common.pojo.PaginationVO;
import com.hzb.erp.api.pc.course.service.CourseService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.EnumTools;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 541720500@qq.com
 */
@RestController
@RequestMapping("/common/course")
@Api(value = "课程管理", tags = "课程管理")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseImageMapper courseImageMapper;

    @Autowired
    private CourseSectionMapper courseSectionMapper;

    @ApiOperation("课程信息")
    @GetMapping("/info")
    public CourseVO info(@RequestParam("id") Long id) {
        return courseService.getInfo(id);
    }


    @ApiOperation("课程列表")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "lessonType", defaultValue = "") Integer lessonType,
                             @RequestParam(value = "state", defaultValue = "") String[] state,
                             @RequestParam(value = "linkId", defaultValue = "") Long linkId,
                             @RequestParam(value = "name", defaultValue = "") String name,
                             @RequestParam(value = "subjectId", defaultValue = "") Long subjectId) {
        CourseParamDTO param = new CourseParamDTO();
        param.setSubjectId(subjectId);
        param.setLessonType(lessonType);
        List<Integer> value = new ArrayList<>();
        for (String s : state) {
            SwitchEnum enumClass = EnumTools.getByDist(s, SwitchEnum.class);
            if (enumClass != null) {
                value.add(enumClass.getCode());
            }
        }
        param.setState(value);

        param.setName(name);
        param.setLinkId(linkId);
        param.setPage(page);
        param.setPageSize(pageSize);

        IPage<CourseVO> res = courseService.getList(param);
        for(CourseVO courseVO: res.getRecords()) {
            QueryWrapper<CourseImage> qw = new QueryWrapper<>();
            qw.eq("course_id", courseVO.getId());
            courseVO.setImages(courseImageMapper.selectList(qw).stream().map(CourseImage::getImageUrl).collect(Collectors.toList()));
        }
        return JsonResponseUtil.paginate(res);
    }

    @ApiOperation("创建和修改课程")
    @Log(description = "创建和修改课程", type = "课程管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody CourseSaveDTO courseSaveDTO, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (courseService.saveOrUpdateByDTO(courseSaveDTO)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("删除课程")
    @Log(description = "删除课程", type = "课程管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        if (courseService.delete(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("启用课程")
    @Log(description = "启用课程", type = "课程管理")
    @PostMapping("/open")
    public JsonResponse open(@RequestBody List<Long> ids) {
        if (courseService.open(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("禁用课程")
    @Log(description = "禁用课程", type = "课程管理")
    @PostMapping("/close")
    public JsonResponse close(@RequestBody List<Long> ids) {
        if (courseService.close(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("关联课程")
    @Log(description = "关联课程", type = "课程管理")
    @GetMapping("/link")
    public JsonResponse link(@RequestParam(value = "courseId") Long courseId,
                             @RequestParam(value = "linkedIds") Long[] linkedIds) {
        if (courseService.handleLink(courseId, linkedIds) > 0) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("添加失败");
        }
    }

    @ApiOperation("取消关联课程")
    @Log(description = "取消关联课程", type = "课程管理")
    @GetMapping("/unlink")
    public JsonResponse unlink(@RequestParam(value = "courseId") Long courseId,
                               @RequestParam(value = "linkedIds") Long[] linkedIds) {
        if (courseService.handleUnlink(courseId, linkedIds) > 0) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("取消关联失败");
        }
    }

    @ApiOperation("课程大纲列表")
    @GetMapping("/courseSectionList")
    public PaginationVO courseSectionList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "courseId") Long courseId) {
        IPage<CourseSection> ipage = new Page<>(page, pageSize);
        QueryWrapper<CourseSection> qw = new QueryWrapper();
        qw.eq("course_id", courseId);
        return JsonResponseUtil.paginate(courseSectionMapper.selectPage(ipage, qw));
    }


    @ApiOperation("创建和修改课程大纲")
    @Log(description = "创建和修改课程大纲", type = "课程管理")
    @PostMapping("/saveCourseSection")
    @PreventMultiSubmit
    public Object saveCourseSection(@Valid @RequestBody CourseSectionDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        CourseSection cs = new CourseSection();
        BeanUtils.copyProperties(dto, cs);
        if (courseSectionMapper.insert(cs) > 0) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("删除课程大纲")
    @Log(description = "删除课程大纲", type = "课程管理")
    @GetMapping("/deleteCourseSection")
    public Object deleteCourseSection(@RequestParam(value = "ids") Long[] ids) {
        if (courseSectionMapper.deleteBatchIds(Arrays.asList(ids)) > 0) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }
}
