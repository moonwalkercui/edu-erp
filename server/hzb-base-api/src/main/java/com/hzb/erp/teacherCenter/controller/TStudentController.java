package com.hzb.erp.teacherCenter.controller;

import com.hzb.erp.annotation.Log;
import com.hzb.erp.common.entity.StaffOrginfo;
import com.hzb.erp.common.mapper.StaffOrginfoMapper;
import com.hzb.erp.common.pojo.dto.StudentBaseInfoDTO;
import com.hzb.erp.common.pojo.dto.StudentCourseParamDTO;
import com.hzb.erp.common.pojo.dto.StudentParamDTO;
import com.hzb.erp.common.pojo.vo.StudentCourseVO;
import com.hzb.erp.common.pojo.vo.StudentVO;
import com.hzb.erp.common.service.SettingService;
import com.hzb.erp.common.service.StudentCourseService;
import com.hzb.erp.common.service.StudentService;
import com.hzb.erp.security.Util.SecurityUtils;
import com.hzb.erp.security.Util.UserAuthUtil;
import com.hzb.erp.service.UserAuthService;
import com.hzb.erp.service.enums.SettingNameEnum;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@RestController
@RequestMapping("/tCenter/student")
@Api(value = "学生相关", tags = "学生相关")
public class TStudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private StaffOrginfoMapper staffOrginfoMapper;
    @Autowired
    private SettingService settingService;

    @ApiOperation("学员信息")
    @GetMapping("/info")
    public StudentVO studentInfo(@RequestParam("id") Long id) {
        return studentService.getBaseInfo(id);
    }

    @ApiOperation("课时的学员列表")
    @GetMapping("/list")
    public Object list(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
            @RequestParam(value = "self", defaultValue = "") Boolean self,
            @RequestParam(value = "classId", defaultValue = "") Long classId,
            @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {
        StudentParamDTO param = new StudentParamDTO();
        param.setClassId(classId);
        if (self != null && self) {
            param.setCreator(UserAuthService.getCurrentUserId());
        }
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setKeyword(keyword);
        return page != null && page > 0 ?
                JsonResponseUtil.paginate(studentService.getList(param)) :
                studentService.getAll(param);
    }

    @ApiOperation("学员课时统计")
    @GetMapping("/courseStats")
    public List<StudentCourseVO> courseStats(@RequestParam(value = "studentId", defaultValue = "") Long studentId) {
        StudentCourseParamDTO param = new StudentCourseParamDTO();
        param.setStudentId(studentId);
        return studentCourseService.getAll(param);
    }

    @ApiOperation("创建和修改学员基本信息")
    @Log(description = "创建和修改学员基本信息", type = "学员管理")
    @PostMapping("/save")
    public JsonResponse saveStudent(@Valid @RequestBody StudentBaseInfoDTO studentDTO, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        Long userId = UserAuthUtil.getCurrentUserId();
        if (studentDTO.getCounselor() == null) {
            studentDTO.setCounselor(userId);
        }
        StaffOrginfo orginfo = staffOrginfoMapper.getByStaffId(studentDTO.getCounselor());
        studentDTO.setSchoolId(orginfo == null ? null : orginfo.getComId());

        if (studentDTO.getId() == null) {
            String defaultPwd = settingService.strValue(SettingNameEnum.STUDENT_DEFAULT_PWD.getCode());
            studentDTO.setPasswordEncode(SecurityUtils.passwordEncode(defaultPwd));
        }

        if (studentService.saveOrUpdateByDTO(studentDTO)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }


}
