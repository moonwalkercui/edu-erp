package com.hzb.erp.adminCenter.controller;


import com.hzb.erp.base.annotation.Log;
import com.hzb.erp.base.annotation.PreventMultiSubmit;
import com.hzb.erp.common.configuration.SystemConfig;
import com.hzb.erp.common.entity.StaffOrginfo;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.entity.User;
import com.hzb.erp.common.enums.GenderEnum;
import com.hzb.erp.common.enums.StudentStageEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.StaffOrginfoMapper;
import com.hzb.erp.common.pojo.dto.ChangePasswordDTO;
import com.hzb.erp.common.pojo.dto.ParentInfoSaveDTO;
import com.hzb.erp.common.pojo.dto.StudentBaseInfoDTO;
import com.hzb.erp.common.pojo.dto.StudentParamDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.pojo.vo.StudentVO;
import com.hzb.erp.common.service.SettingService;
import com.hzb.erp.common.service.StudentService;
import com.hzb.erp.common.service.UserService;
import com.hzb.erp.security.Util.SecurityUtils;
import com.hzb.erp.security.Util.UserAuthUtil;
import com.hzb.erp.service.ImportExportService;
import com.hzb.erp.adminCenter.service.UserAuthService;
import com.hzb.erp.service.enums.SettingNameEnum;
import com.hzb.erp.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author 541720500@qq.com
 */
@RestController
@RequestMapping("/common/student")
@Api(value = "学生管理", tags = "学生管理")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImportExportService importExportService;

    @Autowired
    private StaffOrginfoMapper staffOrginfoMapper;

    @Autowired
    private SettingService settingService;

    @Autowired
    private SystemConfig systemConfig;

    @ApiOperation("学员信息")
    @GetMapping("/info")
    public StudentVO studentInfo(@RequestParam("id") Long id) {
        return studentService.getBaseInfo(id);
    }

    @ApiOperation("学员列表")
    @GetMapping("/list")
    public PaginationVO studentList(
            @RequestParam(value = "self", defaultValue = "false") Boolean self,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
            @RequestParam(value = "userId", defaultValue = "") Long userId,
            @RequestParam(value = "courseId", defaultValue = "") Long courseId,
            @RequestParam(value = "classId", defaultValue = "") Long classId,
            @RequestParam(value = "excludeClassId", defaultValue = "") Long excludeClassId,
            @RequestParam(value = "stage", defaultValue = "") Integer stage,
            @RequestParam(value = "excludeStage", defaultValue = "") Integer excludeStage,
            @RequestParam(value = "grade", defaultValue = "") Integer grade,
            @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        StudentParamDTO param = new StudentParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setKeyword(keyword);
        param.setStage(stage);
        param.setClassId(classId);
        param.setExcludeClassId(excludeClassId);
        param.setUserId(userId);
        param.setCourseId(courseId);
        param.setExcludeStage(excludeStage);
        param.setGrade(grade);
        if (self != null && self) {
            param.setCreator(UserAuthService.getCurrentUserId());
        }
        return JsonResponseUtil.paginate(studentService.getList(param));
    }

    @ApiOperation("创建和修改学员基本信息")
    @Log(description = "创建和修改学员基本信息", type = "学员管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse saveStudent(@Valid @RequestBody StudentBaseInfoDTO studentDTO, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        Long userId = UserAuthUtil.getCurrentUserId();
        if (studentDTO.getCounselor() == null) {
            studentDTO.setCounselor(userId);
        }
        StaffOrginfo orginfo = staffOrginfoMapper.getByStaffId(studentDTO.getCounselor());
        studentDTO.setSchoolId(orginfo == null ? null : orginfo.getComId());

        if (studentDTO.getId() == null) {
            String defaultPwd = settingService.strValue(SettingNameEnum.STUDENT_DEFAULT_PWD.getCode(), "123456");
            studentDTO.setPasswordEncode(SecurityUtils.passwordEncode(defaultPwd));
        }

        if (studentService.saveOrUpdateByDTO(studentDTO)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("变为在学学员")
    @Log(description = "变为在学学员", type = "学员管理")
    @PostMapping("/changeLearning")
    public JsonResponse changeLearning(@RequestBody List<Long> ids) {
        if (studentService.changeStage(ids, StudentStageEnum.STUDYING, true)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("变为意向学员")
    @Log(description = "变为意向学员", type = "学员管理")
    @PostMapping("/changeIntention")
    public JsonResponse changeIntention(@RequestBody List<Long> ids) {
        if (studentService.changeStage(ids, StudentStageEnum.INTENTION, true)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("变为结业学员")
    @Log(description = "变为结业学员", type = "学员管理")
    @PostMapping("/changeGraduation")
    public JsonResponse changeGraduation(@RequestBody List<Long> ids) {
        if (studentService.changeStage(ids, StudentStageEnum.GRADUATION, true)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("删除学员")
    @Log(description = "删除学员", type = "学员管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        Student student;
        List<Long> deleteIds = new ArrayList<>();
        for (Long id : ids) {
            student = studentService.getById(id);
            if (StudentStageEnum.INTENTION.equals(student.getStage())) {
                deleteIds.add(student.getId());
            }
        }
        if (studentService.delete(deleteIds)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("删除失败,只有意向学员可以被删除");
        }
    }

    @ApiOperation("导出模板")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) {
        importExportService.exportExcel(response, excelHeaderMap(), null, "学员导入模板");
    }

    @ApiOperation("导入")
    @Log(description = "导入")
    @PostMapping("/import")
    @PreventMultiSubmit
    public JsonResponse importExcel(@RequestParam("file") MultipartFile file, @RequestParam(value = "stage") Integer stage) {
        List<Map<String, Object>> importDate = importExportService.importExcel(file, excelHeaderMap());
        if (importDate == null || importDate.size() == 0) {
            return JsonResponseUtil.error("未导入数据");
        }
        if (ImportExportService.isRowNotUnique(importDate, "name")) {
            return JsonResponseUtil.error("无法导入:姓名有重复");
        }
        if (ImportExportService.isRowNotUnique(importDate, "mobile")) {
            return JsonResponseUtil.error("无法导入:手机号码有重复");
        }

        List<Student> records = new ArrayList<>();

        for (Map<String, Object> map : importDate) {

            String mobile = map.get("mobile").toString().trim();
            String name = map.get("name").toString().trim();
            String gender = map.get("gender") != null ? map.get("gender").toString().trim() : null;
            String idcard = map.get("idcard") != null ? map.get("idcard").toString().trim() : null;
            String password = map.get("password") != null ? map.get("password").toString().trim() : null;
            String parentname = map.get("parent") != null ? map.get("parent").toString().trim() : name + "的家长";

            if (password == null) {
                throw new BizException("缺少密码:" + name);
            }

            LocalDate birthday = null;
            if (map.get("birthday") != null && StringUtils.isNotBlank(map.get("birthday").toString())) {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                birthday = LocalDateTime.parse(map.get("birthday").toString(), df).toLocalDate();
            }

            GenderEnum genderEum = EnumTools.getByDist(gender, GenderEnum.class);
            if (genderEum == null) {
                genderEum = GenderEnum.UNKNOWN;
            }

            StudentStageEnum stageEnum = EnumTools.getByCode(stage, StudentStageEnum.class);
            if (stageEnum == null) {
                stageEnum = StudentStageEnum.INTENTION;
            }

            String defaultPwd = settingService.strValue(SettingNameEnum.STUDENT_DEFAULT_PWD.getCode());
            User user = userService.existOrCreate(mobile, parentname, SecurityUtils.passwordEncode(defaultPwd));

            Student item = new Student();
            item.setName(name);
            item.setUserId(user.getId());
            item.setGender(genderEum);
            item.setStage(stageEnum);
            item.setBirthday(birthday);
            item.setIdcard(idcard);
            Long staffId = UserAuthUtil.getCurrentUserId();
            StaffOrginfo orginfo = staffOrginfoMapper.getByStaffId(staffId);
            item.setCounselor(staffId);
            item.setSchoolId(orginfo == null ? null : orginfo.getComId());

            records.add(item);
        }
        return studentService.saveBatch(records) ?
                JsonResponseUtil.success("成功导入" + importDate.size() + "条记录") :
                JsonResponseUtil.error("导入出错");
    }

    private Map<String, String> excelHeaderMap() {
        return new LinkedHashMap<String, String>() {{
            put("name", "*姓名");
            put("mobile", "*手机号");
            put("password", "*登录密码");
            put("parent", "家长姓名");
            put("gender", "性别(有效值:男|女|未知)");
            put("birthday", "生日(格式:2000-01-01)");
            put("idcard", "身份证");
        }};
    }

    @ApiOperation("账号列表")
    @GetMapping("/userList")
    public PaginationVO userList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        StudentParamDTO param = new StudentParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setKeyword(keyword);
        return JsonResponseUtil.paginate(userService.getList(param));
    }

    @ApiOperation("创建家长信息")
    @Log(description = "创建家长信息", type = "学员管理")
    @PostMapping("/saveParentInfo")
    @PreventMultiSubmit
    public JsonResponse saveParentInfo(@Valid @RequestBody ParentInfoSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (studentService.saveParentInfo(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("修改学员账号密码")
    @Log(description = "修改学员账号密码", type = "学员管理")
    @PostMapping("/changePassword")
    public JsonResponse changePassword(@Valid @RequestBody ChangePasswordDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        dto.setPasswordEncode(SecurityUtils.passwordEncode(dto.getPassword()));
        if (userService.changPassword(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("密码设置失败");
        }
    }

    @ApiOperation("修改学员端密码")
    @Log(description = "修改学员端密码", type = "学员管理")
    @PostMapping("/changeUserPassword")
    public JsonResponse changeUserPassword(@Valid @RequestBody ChangePasswordDTO dto, BindingResult result) {
        if (systemConfig.getIsDemo()) {
            return JsonResponseUtil.error("DEMO版此处不能操作"); // todo 发行
        }
        CommonUtil.handleValidMessage(result);
        dto.setPasswordEncode(SecurityUtils.passwordEncode(dto.getPassword()));
        if (userService.changPassword(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("密码设置失败");
        }
    }

    @ApiOperation("修改头像")
    @Log(description = "修改头像", type = "学员管理")
    @GetMapping("/changeHeadImg")
    public JsonResponse changeHeadImg(@RequestParam(value = "studentId", defaultValue = "") Long studentId,
                                      @RequestParam(value = "img", defaultValue = "") String img) {
        if (studentService.changeHeadImg(studentId, img)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error();
        }
    }

    @ApiOperation("修改顾问")
    @Log(description = "修改顾问", type = "学员管理")
    @GetMapping("/changeCounselor")
    public JsonResponse changeCounselor(@RequestParam(value = "studentId", defaultValue = "") Long studentId,
                                        @RequestParam(value = "staffId", defaultValue = "") Long staffId) {
        if (studentService.changeCounselor(studentId, staffId)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error();
        }
    }
}
