package com.hzb.erp.studentCenter.controller;

import com.hzb.erp.annotation.Log;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.pojo.dto.HomeworkParamDTO;
import com.hzb.erp.common.pojo.dto.HomeworkRecordSaveDTO;
import com.hzb.erp.common.pojo.vo.HomeworkVO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.service.HomeworkRecordService;
import com.hzb.erp.common.service.HomeworkService;
import com.hzb.erp.common.service.StudentService;
import com.hzb.erp.service.UserAuthService;
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

    @Autowired
    private StudentService studentService;

    @ApiOperation("作业信息")
    @GetMapping("/info")
    public HomeworkVO info(@RequestParam("id") Long id) {
        Student student = UserAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        Long studentId = student.getId();
        return homeworkService.getInfo(id, studentId);
    }

    @ApiOperation("作业列表")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize) {
        HomeworkParamDTO param = new HomeworkParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        Student student = UserAuthService.getCurrentStudent();
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
        Student student = UserAuthService.getCurrentStudent();
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
        Student student = UserAuthService.getCurrentStudent();
        Long studentId = student.getId();
        dto.setStudentId(studentId);
        if (homeworkRecordService.saveRecord(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("提交失败");
        }
    }

}
