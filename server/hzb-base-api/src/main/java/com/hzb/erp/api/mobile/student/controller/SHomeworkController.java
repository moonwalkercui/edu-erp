package com.hzb.erp.api.mobile.student.controller;

import com.hzb.erp.api.base.annotation.Log;
import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.api.pc.lesson.pojo.HomeworkParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.HomeworkRecordSaveDTO;
import com.hzb.erp.api.pc.lesson.pojo.HomeworkVO;
import com.hzb.erp.common.pojo.PaginationVO;
import com.hzb.erp.api.pc.lesson.service.HomeworkRecordService;
import com.hzb.erp.api.pc.lesson.service.HomeworkService;
import com.hzb.erp.api.mobile.student.service.StudentAuthService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@RestController
@RequestMapping("/sCenter/homework")
@Api(value = "作业", tags = "作业")
public class SHomeworkController {

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private HomeworkRecordService homeworkRecordService;

    @ApiOperation("作业信息")
    @GetMapping("/info")
    public HomeworkVO info(@RequestParam("id") Long id) {
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        Long studentId = student.getId();
        return homeworkService.getInfo(id, studentId);
    }

    @ApiOperation("作业列表")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        HomeworkParamDTO param = new HomeworkParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        Long studentId = student.getId();
        param.setStudentId(studentId);

        return JsonResponseUtil.paginate(homeworkService.getList(param));
    }

    @ApiOperation("删除作业")
    @Log(description = "删除作业", type = "学生端", isStaff = false)
    @PostMapping("/deleteRecord/{id}")
    public JsonResponse deleteRecord(@PathVariable("id") Long id) {
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return JsonResponseUtil.error("请先添加学生");
        }
        Long studentId = student.getId();
        if (homeworkRecordService.deleteByStudentId(id, studentId)) {
            return JsonResponseUtil.success("已删除");
        } else {
            return JsonResponseUtil.error("删除失败");
        }
    }

    @ApiOperation("提交作业")
    @Log(description = "提交作业", type = "学生端", isStaff = false)
    @PostMapping("/saveRecord")
    public JsonResponse saveRecord(@Valid @RequestBody HomeworkRecordSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return JsonResponseUtil.error("请先添加学生");
        }
        Long studentId = student.getId();
        dto.setStudentId(studentId);
        if (homeworkRecordService.saveRecord(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("提交失败");
        }
    }

}
