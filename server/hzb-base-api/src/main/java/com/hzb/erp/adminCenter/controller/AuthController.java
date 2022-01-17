package com.hzb.erp.adminCenter.controller;

import com.hzb.erp.common.entity.rbac.SysRole;
import com.hzb.erp.common.service.RbacService;
import com.hzb.erp.security.Util.JwtUserDetails;
import com.hzb.erp.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户控制器
 * </p>
 *
 * @author 541720500@qq.com
 */
@RestController
@RequestMapping("/common/auth")
@Api(value = "用户控制器", tags = "用户控制器")
public class AuthController {

    @Resource
    private RbacService rbacService;

    @ApiOperation("获取登录者信息")
    @GetMapping("/currentUser")
    public Map<String, Object> currentUser() {
        JwtUserDetails curUser = UserAuthService.getCurrentUser();

        List<Long> roleIds = new ArrayList<>();
        for (SysRole role : curUser.getRoles()) {
            roleIds.add(role.getId());
        }

        List<String> pmCodes = rbacService.getPermissionCodesByRoleIds(roleIds);

        List<String> roles = new ArrayList<>();
        for (SysRole role : curUser.getRoles()) {
            roles.add(role.getCode());
        }

        Map<String, Object> user = new HashMap<>();
        user.put("id", curUser.getId());
        user.put("name", curUser.getName());
        user.put("mobile", curUser.getUsername());
        user.put("avatar", "img");
        user.put("roles", roles);
        user.put("permissions", pmCodes);
        return user;

    }
}
