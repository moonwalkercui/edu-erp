package com.hzb.erp.api.mobile.student.controller;

import com.hzb.erp.api.base.annotation.Log;
import com.hzb.erp.configuration.SystemConfig;
import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.api.pc.student.mapper.StudentMapper;
import com.hzb.erp.api.pc.clazz.pojo.GradeParamDTO;
import com.hzb.erp.api.pc.student.pojo.StudentRegisterDTO;
import com.hzb.erp.common.pojo.PaginationVO;
import com.hzb.erp.api.pc.student.pojo.StudentVO;
import com.hzb.erp.api.pc.clazz.service.GradeRecordService;
import com.hzb.erp.api.pc.student.service.StudentService;
import com.hzb.erp.security.Util.UserAuthUtil;
import com.hzb.erp.service.FileService;
import com.hzb.erp.api.mobile.student.service.StudentAuthService;
import com.hzb.erp.service.bo.UploadResultBO;
import com.hzb.erp.service.bo.UploadValidateBO;
import com.hzb.erp.api.mobile.student.pojo.vo.StudentVo;
import com.hzb.erp.api.mobile.teacher.controller.TStudentController;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@RestController
@RequestMapping("/sCenter/student")
@Api(value = "学生接口", tags = "学生接口")
public class SStudentController {

    @Autowired
    private StudentService studentService;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private SystemConfig systemConfig;

    @Resource
    private FileService fileService;

    @Autowired
    private TStudentController tStudentController;

    @Autowired
    private GradeRecordService gradeRecordService;

    @ApiOperation("学员信息")
    @GetMapping("/info")
    public StudentVO studentInfo(@RequestParam(value = "id", defaultValue = "") Long id) {
        if (id == null) {
            return this.currentStudent();
        }
        return studentMapper.getBaseInfoByUid(id, StudentAuthService.getCurrentUserId());
    }

    @ApiOperation("当前登录学生信息")
    @GetMapping("/currentStudent")
    public StudentVO currentStudent() {
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        return studentMapper.getBaseInfo(student.getId());
    }

    @ApiOperation("未读数量")
    @GetMapping("/redpoint")
    public Map<String, Object> redpoint() {
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        Long studentId = student.getId();
        int flag = 0;
        if (student.getRedpointEvaluate() == null) {
            student.setRedpointEvaluate(LocalDateTime.now());
            flag++;
        }
        if (student.getRedpointGrade() == null) {
            student.setRedpointGrade(LocalDateTime.now());
            flag++;
        }
        if (flag > 0) {
            studentService.updateById(student);
        }

        return studentMapper.getRedpointCounts(studentId);
    }

    @ApiOperation("课时的学员列表")
    @GetMapping("/list")
    public Object list(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam(value = "classId", defaultValue = "") Long classId
    ) {
        return tStudentController.list(null, pageSize, false, classId, "");
    }

    @ApiOperation("成绩单列表")
    @GetMapping("/gradeRecord")
    public PaginationVO gradeRecord(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        GradeParamDTO param = new GradeParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        Student student = StudentAuthService.getCurrentStudent();
        if (student == null) {
            return null;
        }
        Long studentId = student.getId();
        param.setStudentId(studentId);
        student.setRedpointGrade(LocalDateTime.now());
        studentService.updateById(student);

        return JsonResponseUtil.paginate(gradeRecordService.getList(param));
    }

    @PostMapping("/uploadAvatar")
    @ResponseBody
    public UploadResultBO uploadAvatar(@RequestParam("file") MultipartFile file) throws FileUploadException {
        UploadValidateBO validateBO = new UploadValidateBO();
        validateBO.setMaxSize(systemConfig.getUploadImgMaxSize());
        validateBO.setIsImage(true);
        return fileService.upload(file, validateBO, null);
    }

    @ApiOperation("学生列表")
    @GetMapping("/studentList")
    public List<StudentVo> studentList() {
        Long uid = UserAuthUtil.getCurrentUserId();
        List<StudentVo> res = new ArrayList<>();
        List<Student> students = studentMapper.listByUserId(uid);
        for (Student s : students) {
            StudentVo item = new StudentVo();
            BeanUtils.copyProperties(s, item);
            res.add(item);
        }
        return res;
    }

    @ApiOperation("移除学生")
    @Log(description = "移除学生", type = "学生端", isStaff = false)
    @GetMapping("/deleteStudent")
    public JsonResponse deleteStudent(@RequestParam(value = "id") Long id) {
        if (systemConfig.getIsDemo()) {
            return JsonResponseUtil.error("测试账号不能操作");
        }
        if (studentService.handleDelByUser(id, UserAuthUtil.getCurrentUserId())) {
            return JsonResponseUtil.success("已移除");
        } else {
            return JsonResponseUtil.error();
        }
    }

    @ApiOperation("添加学生")
    @Log(description = "添加学生", type = "学生端", isStaff = false)
    @PostMapping("/register")
    public JsonResponse register(@Valid @RequestBody StudentRegisterDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (studentService.saveOrUpdateByUser(dto, UserAuthUtil.getCurrentUserId())) {
            return JsonResponseUtil.success("已添加");
        } else {
            return JsonResponseUtil.error();
        }
    }

    @ApiOperation("切换学生")
    @Log(description = "切换学生", type = "学生端", isStaff = false)
    @GetMapping("/switchStudent")
    public JsonResponse switchStudent(@RequestParam(value = "id") Long id) {
        if (studentService.switchStudent(id, UserAuthUtil.getCurrentUserId())) {
            return JsonResponseUtil.success("已移除");
        } else {
            return JsonResponseUtil.error();
        }
    }
}
