package com.hzb.erp.adminCenter.controller;


import com.hzb.erp.annotation.Log;
import com.hzb.erp.annotation.PreventMultiSubmit;
import com.hzb.erp.common.configuration.SystemConfig;
import com.hzb.erp.common.enums.StaffStateEnum;
import com.hzb.erp.common.pojo.dto.ChangePasswordDTO;
import com.hzb.erp.common.pojo.dto.StaffChangeStateParamDTO;
import com.hzb.erp.common.pojo.dto.StaffParamDTO;
import com.hzb.erp.common.pojo.dto.StaffSaveDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.common.pojo.vo.StaffVO;
import com.hzb.erp.common.service.StaffService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.security.Util.SecurityUtils;
import com.hzb.erp.service.UserAuthService;
import com.hzb.erp.utils.EnumTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 教师员工表 前端控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/staff")
@Api(value = "员工管理", tags = "员工管理")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private SystemConfig systemConfig;

    @ApiOperation("老师信息")
    @GetMapping("/info")
    public StaffVO info(@RequestParam("id") Long id) {
        return staffService.getInfo(id);
    }

    @ApiOperation("员工列表")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "orgId", defaultValue = "") Long orgId,
                             @RequestParam(value = "state", defaultValue = "") String state,
                             @RequestParam(value = "isManager", defaultValue = "") Boolean isManager,
                             @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        StaffParamDTO param = new StaffParamDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        param.setKeyword(keyword);
        param.setIsManager(isManager);
        param.setOrgId(orgId);
        StaffStateEnum byDist = EnumTools.getByDist(state, StaffStateEnum.class);
        if (byDist != null) {
            param.setState(byDist.getCode());
        }
        return JsonResponseUtil.paginate(staffService.getList(param));
    }

    @ApiOperation("创建和修改老师资料")
    @Log(description = "创建和修改老师资料", type = "师资管理")
    @PostMapping("/save")
    @PreventMultiSubmit
    public JsonResponse save(@Valid @RequestBody StaffSaveDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (systemConfig.getIsDemo()) {
            return JsonResponseUtil.error("DEMO版此处不能操作"); // todo 发行
        }
        dto.setCreator(UserAuthService.getCurrentUserId());
        if (staffService.saveOrUpdateByDTO(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("删除老师")
    @Log(description = "删除老师", type = "师资管理")
    @PostMapping("/delete")
    public JsonResponse delete(@RequestBody List<Long> ids) {
        if (systemConfig.getIsDemo()) {
            return JsonResponseUtil.error("DEMO版此处不能操作"); // todo 发行
        }
        if (staffService.deleteTeacher(ids)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("修改头像")
    @Log(description = "修改头像", type = "师资管理")
    @GetMapping("/changeHeadImg")
    public JsonResponse changeHeadImg(@RequestParam(value = "staffId", defaultValue = "") Long staffId,
                                      @RequestParam(value = "img", defaultValue = "") String img) {
        if (staffService.changeHeadImg(staffId, img)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("切换在职状态")
    @Log(description = "切换在职状态", type = "师资管理")
    @PostMapping("/changeState")
    public JsonResponse changeState(@RequestBody StaffChangeStateParamDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (systemConfig.getIsDemo()) {
            return JsonResponseUtil.error("DEMO版此处不能操作"); // todo 发行
        }
        if (staffService.changeState(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("操作失败");
        }
    }

    @ApiOperation("修改员工密码")
    @Log(description = "修改员工密码", type = "员工管理")
    @PostMapping("/changePassword")
    public JsonResponse changePassword(@Valid @RequestBody ChangePasswordDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);

        if (systemConfig.getIsDemo()) {
            return JsonResponseUtil.error("DEMO版此处不能操作"); // todo 发行
        }
        dto.setPasswordEncode(SecurityUtils.passwordEncode(dto.getPassword()));
        if (staffService.changePassword(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("密码设置失败");
        }
    }

    @ApiOperation("重置密码")
    @Log(description = "重置密码", type = "员工管理")
    @GetMapping("/resetPassword")
    public JsonResponse resetPassword(@RequestParam(value = "password") String password) {
        if (systemConfig.getIsDemo()) {
            return JsonResponseUtil.error("DEMO版此处不能操作"); // todo 发行
        }
        ChangePasswordDTO dto = new ChangePasswordDTO();
        dto.setId(UserAuthService.getCurrentUserId());
        dto.setPassword(password);
        dto.setPasswordEncode(SecurityUtils.passwordEncode(password));

        if (staffService.changePassword(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("密码设置失败");
        }
    }

    @ApiOperation("未读消息数")
    @GetMapping("/messageCount")
    public Integer list() {
        // todo
        return 1;
    }
}
