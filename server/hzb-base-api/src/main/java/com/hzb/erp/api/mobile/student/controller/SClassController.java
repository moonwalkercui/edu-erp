package com.hzb.erp.api.mobile.student.controller;

import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.api.pc.clazz.pojo.ClassParamDTO;
import com.hzb.erp.api.pc.clazz.pojo.ClassVO;
import com.hzb.erp.api.pc.clazz.service.ClazzService;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.api.mobile.student.service.StudentAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@RestController
@RequestMapping("/sCenter/class")
@Api(value = "班级管理", tags = "班级管理")
public class SClassController {
    @Autowired
    private ClazzService clazzService;

    @ApiOperation("班级信息")
    @GetMapping("/info")
    public ClassVO info(@RequestParam(value = "id") Long id) {
        return clazzService.getInfo(id);
    }

    @ApiOperation("课程列表")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "") Integer page,
                       @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        ClassParamDTO param = new ClassParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        Long studentId = student.getId();
        param.setStudentId(studentId);
        return page != null && page > 0 ?
                JsonResponseUtil.paginate(clazzService.getList(param)) :
                clazzService.getAll(param);
    }

}
