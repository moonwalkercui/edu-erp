package com.hzb.erp.studentCenter.controller;

import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.pojo.dto.StudentCourseParamDTO;
import com.hzb.erp.common.service.StudentCourseService;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@RestController
@RequestMapping("/sCenter/course")
@Api(value = "课程接口", tags = "课程接口")
public class SCourseController {

    @Autowired
    private StudentCourseService studentCourseService;

    @ApiOperation("学员课次统计")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "page", defaultValue = "") Integer page,
                       @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                       @RequestParam(value = "excludeExpired", defaultValue = "") Boolean excludeExpired,
                       @RequestParam(value = "startDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                       @RequestParam(value = "endDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        StudentCourseParamDTO param = new StudentCourseParamDTO();
        Student student = UserAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        Long studentId = student.getId();
        param.setStudentId(studentId);
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setStartDate(startDate);
        param.setEndDate(endDate);
        param.setExcludeExpired(excludeExpired);
        return page != null && page > 0 ?
                JsonResponseUtil.paginate(studentCourseService.getList(param)) :
                studentCourseService.getAll(param);
    }

}
