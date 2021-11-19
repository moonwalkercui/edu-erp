package com.hzb.erp.security.Util;

import com.hzb.erp.common.entity.StaffOrginfo;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.service.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Ryan
 * description 通用服务
 **/
@Service
@Slf4j
public class UserAuthUtil {

    /**
     * 获取登录者信息
     */
    public static Long getCurrentUserId() {
        JwtUserDetails userDetails = getUserInContext();
        return userDetails == null ? null :userDetails.getId();
    }

    /**
     * 从token里获取当前登录者信息
     */
    public static JwtUserDetails getCurrentUser() {
        JwtUserDetails userDetails = getUserInContext();
        log.info("=========> 当前登录者 : {}", getUserInContext());
        return userDetails;
    }

    /**
     * 从token里获取当前登录者信息
     */
    public static Student getCurrentStudent() {
        Long userId = getCurrentUserId();
        return UserAuthService.getDefaultStudentByUserId(userId);
    }

    /**
     * 上下文里的用户 来自与token
     */
    private static JwtUserDetails getUserInContext() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        return auth == null ? null : (JwtUserDetails) auth.getPrincipal();
    }

}
