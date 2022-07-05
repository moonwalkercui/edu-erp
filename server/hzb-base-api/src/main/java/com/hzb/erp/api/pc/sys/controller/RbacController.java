package com.hzb.erp.api.pc.sys.controller;


import com.hzb.erp.api.base.annotation.Log;
import com.hzb.erp.api.base.annotation.PreventMultiSubmit;
import com.hzb.erp.configuration.SystemConfig;
import com.hzb.erp.common.entity.rbac.SysPermission;
import com.hzb.erp.common.entity.rbac.SysRole;
import com.hzb.erp.common.pojo.BatchBindRoleToUserDTO;
import com.hzb.erp.common.pojo.PaginateDTO;
import com.hzb.erp.common.pojo.PaginationVO;
import com.hzb.erp.common.service.RbacService;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限管理控制器
 * </p>
 *
 * @author Ryan
 */
@RestController
@RequestMapping("/common/rbac")
@Api(value = "权限管理", tags = "权限管理")
public class RbacController {

    @Autowired
    private RbacService rbacService;

    @Autowired
    private SystemConfig systemConfig;

    @ApiOperation("角色列表")
    @GetMapping("/roleList")
    public PaginationVO roleList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize) {

        PaginateDTO param = new PaginateDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        return JsonResponseUtil.paginate(rbacService.getRoleList(param));
    }

    @ApiOperation("获取角色的用户列表")
    @GetMapping("/getUsersByRoleId")
    public PaginationVO getUsersByRoleId(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                                         @RequestParam(value = "roleId", defaultValue = "") Long roleId) {

        PaginateDTO param = new PaginateDTO();
        param.setPage(page);
        param.setPageSize(pageSize);
        return JsonResponseUtil.paginate(rbacService.getUsersByRoleId(param, roleId));
    }

    @ApiOperation("获取权限列表")
    @GetMapping("/getPermissionsList")
    public List<SysPermission> getPermissionsList() {
        return rbacService.getPermissionsList();
    }

    @ApiOperation("通过角色id获取权限列表")
    @GetMapping("/getPermissionsByRoleId")
    public List<SysPermission> getPermissionsByRoleId(@RequestParam(value = "roleId") Long roleId) {
        return rbacService.getPermissionsByRoleId(roleId);
    }

    @ApiOperation("保存角色")
    @Log(description = "保存角色", type = "权限管理")
    @PostMapping("/saveRole")
    @PreventMultiSubmit
    public JsonResponse saveRole(@RequestBody SysRole role) {
        if (rbacService.saveRole(role) > 0) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error();
        }
    }

    @ApiOperation("删除角色")
    @Log(description = "删除角色", type = "权限管理")
    @PostMapping("/deleteRole")
    public JsonResponse deleteRole(@RequestParam(value = "id") Long id) {
        if (systemConfig.getIsDemo()) {
            return JsonResponseUtil.error("DEMO版此处不能操作"); // todo 发行
        }
        if (rbacService.deleteRole(id) > 0) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("未删除");
        }
    }

    @ApiOperation("绑定角色和用户")
    @Log(description = "绑定角色和用户", type = "权限管理")
    @PostMapping("/bindRoleToUsers")
    public JsonResponse bindRoleToUsers(@RequestParam(value = "roleId") Long roleId, @RequestParam(value = "userIds") String userIds) {
        List<Long> ids = Arrays.stream(userIds.split(",")).map(Long::valueOf).collect(Collectors.toList());
        if (rbacService.bindRoleToUsers(roleId, ids) > 0) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("未绑定成功");
        }
    }

    @ApiOperation("批量绑定角色和用户")
    @Log(description = "批量绑定角色和用户", type = "权限管理")
    @PostMapping("/batchBindRoleToUsers")
    public JsonResponse batchBindRoleToUsers(@Valid @RequestBody BatchBindRoleToUserDTO dto, BindingResult result) {
        CommonUtil.handleValidMessage(result);
        if (rbacService.batchBindRoleToUsers(dto)) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("未绑定成功");
        }
    }

    @ApiOperation("解绑角色和用户")
    @Log(description = "解绑角色和用户", type = "权限管理")
    @PostMapping("/deleteRoleUser")
    public JsonResponse deleteRoleUser(@RequestParam(value = "roleId") Long roleId, @RequestParam(value = "userIds") String userIds) {
        if (systemConfig.getIsDemo()) {
            return JsonResponseUtil.error("DEMO版此处不能操作"); // todo 发行
        }
        List<Long> ids = Arrays.stream(userIds.split(",")).map(Long::valueOf).collect(Collectors.toList());
        if (rbacService.deleteRoleUser(roleId, ids) > 0) {
            return JsonResponseUtil.success();
        } else {
            return JsonResponseUtil.error("未删除");
        }
    }

    @ApiOperation("解绑角色和权限")
    @Log(description = "解绑角色和权限", type = "权限管理")
    @PostMapping("/bindPermissionsToRole")
    public JsonResponse bindPermissionsToRole(@RequestParam(value = "roleId") Long roleId, @RequestParam(value = "permissionIds") String permissionIds) {

        if (systemConfig.getIsDemo()) {
            return JsonResponseUtil.error("DEMO版此处不能操作"); // todo 发行
        }

        List<Long> ids = Arrays.stream(permissionIds.split(",")).map(Long::valueOf).collect(Collectors.toList());
        if (rbacService.bindPermissionsToRole(roleId, ids) > 0) {
            return JsonResponseUtil.success("已保存。相关账号重新登录后生效。");
        } else {
            return JsonResponseUtil.error("未删除");
        }
    }

}
