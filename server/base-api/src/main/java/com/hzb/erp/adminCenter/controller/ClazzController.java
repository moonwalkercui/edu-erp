package com.hzb.erp.adminCenter.controller;


import com.hzb.erp.annotation.Log;
import com.hzb.erp.annotation.PreventMultiSubmit;
import com.hzb.erp.common.pojo.dto.ClassParamDTO;
import com.hzb.erp.common.pojo.dto.ClassSaveDTO;
import com.hzb.erp.common.pojo.dto.StudentParamDTO;
import com.hzb.erp.common.pojo.vo.ClassVO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.ClassStudentService;
import com.hzb.erp.common.service.ClazzService;
import com.hzb.erp.common.service.StudentService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 班级 前端控制器
 * </p>
 *
 * @author 541720500@qq.com
 */
@RestController
@RequestMapping("/common/class")
@Api(value = "班级管理", tags = "班级管理")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassStudentService classStudentService;

    @ApiOperation("班级列表")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "courseId", defaultValue = "") Long courseId,
                             @RequestParam(value = "studentId", defaultValue = "") Long studentId,
                             @RequestParam(value = "name", defaultValue = "") String name,
                             @RequestParam(value = "over", defaultValue = "false") Boolean over,
                             @RequestParam(value = "teacherId", defaultValue = "") Long teacherId) {
        ClassParamDTO param = new ClassParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setTeacherId(teacherId);
        param.setStudentId(studentId);
        param.setCourseId(courseId);
        param.setClassName(name);
        param.setOver(over);

        return JsonResponseUtil.paginate(clazzService.getList(param));
    }

    @ApiOperation("班级信息")
    @GetMapping("/info")
    public ClassVO info(@RequestParam(value = "id") Long id) {
        return clazzService.getInfo(id);
    }

    @ApiOperation("创建和修改班级")
    @Log(description = "创建和修改班级", type = "班级管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody ClassSaveDTO classSaveDTO, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (clazzService.saveOrUpdateByDTO(classSaveDTO)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("提交失败");
        }
    }

    @ApiOperation("删除班级")
    @Log(description = "删除班级", type = "班级管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        if (clazzService.removeByIds(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

    @ApiOperation("结业班级")
    @Log(description = "结业班级")
    @PostMapping("/over")
    public JsonResponse over(@RequestBody List<Long> ids) {
        if (clazzService.over(ids, UserAuthService.getCurrentUserId())) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

    @ApiOperation("班级学员列表")
    @GetMapping("/studentList")
    public PaginationVO studentList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                                    @RequestParam(value = "classId") Long classId,
                                    @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        StudentParamDTO param = new StudentParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setKeyword(keyword);
        param.setClassId(classId);
        return JsonResponseUtil.paginate(studentService.getListByClassId(param));
    }

}
