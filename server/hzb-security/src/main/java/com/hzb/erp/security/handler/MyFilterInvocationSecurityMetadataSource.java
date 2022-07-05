package com.hzb.erp.security.handler;


import com.hzb.erp.constants.CommonConst;
import com.hzb.erp.common.entity.rbac.SysPermissionDTO;
import com.hzb.erp.common.entity.rbac.SysRole;
import com.hzb.erp.common.mapper.rbac.SysPermissionMapper;
import com.hzb.erp.common.mapper.rbac.SysRoleMapper;
import com.hzb.erp.security.Util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * description: 获取有权访问当前url的角色列表
 */
@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 获取访问路径所需要的角色id列表 每次请求路过
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        String[] roles;

        // 访问sCenter开头的请求 需要学生身份
        if (antPathMatcher.match("/sCenter/**", requestUrl)) {
            roles = new String[]{String.valueOf(CommonConst.STUDENT_ROLE_ID)};
            return SecurityConfig.createList(roles);
        }

        // 如果是学生，不能访问sCenter外的请求
        Boolean isStudent = SecurityUtils.isStudent();
        if (isStudent != null && isStudent) {
            roles = new String[]{String.valueOf(CommonConst.ACCESS_DENIED_ID)};
            return SecurityConfig.createList(roles);
        }
        List<SysPermissionDTO> allPermission = sysPermissionMapper.getAllBaseInfo();
        for (SysPermissionDTO permission : allPermission) {
            if (permission.getUrl() != null && antPathMatcher.match(permission.getUrl(), requestUrl)) {

                List<SysRole> roleList = sysRoleMapper.selectRoleByPermission(permission.getId());
                if (roleList.size() > 0) {
                    roles = roleList.stream().map(r -> String.valueOf(r.getId())).toArray(String[]::new);
                    return SecurityConfig.createList(roles);
                }

            }
        }
        // 如果没有匹配，则默认全部可以访问
        return SecurityConfig.createList();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

}