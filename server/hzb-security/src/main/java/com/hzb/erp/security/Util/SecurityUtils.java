package com.hzb.erp.security.Util;

import cn.hutool.json.JSONUtil;
import com.hzb.erp.constants.CommonConst;
import com.hzb.erp.common.entity.rbac.SysRole;
import com.hzb.erp.utils.*;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
public class SecurityUtils {

    /**
     * 判断是不是学生登录的
     */
    public static Boolean isStudent() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication == null) {
            System.out.println("null了");
            return null;
        }
        Object principal = authentication.getPrincipal();
        if ("anonymousUser".equals(String.valueOf(principal))) {
            return null;
        }

        JwtUserDetails userDetails = (JwtUserDetails) principal;
        for (SysRole role : userDetails.getRoles()) {
            if (CommonConst.STUDENT_ROLE_ID.equals(role.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是不是员工登录的
     */
    public static Boolean isStaff() {
        Boolean isStu = isStudent();
        if (isStu == null) {
            return null;
        }
        return !isStu;
    }

    /**
     * token过滤器里的方法
     */
    public static Claims checkTokenAtFilterInternal(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String token = findTokenOnRequest(request);

        if (StringUtils.isEmpty(token)) {
            JsonResponse res = JsonResponseUtil.error(ResponseCodeEnums.TOKEN_NOTFOUND);
            CommonUtil.jsonResponse(response, JSONUtil.toJsonStr(res));
            return null;
        }
        Claims claims = JwtUtil.parseJWT(token);
        if (JwtUtil.isTokenExpired(claims)) {
            JsonResponse res = JsonResponseUtil.error(ResponseCodeEnums.TOKEN_EXPIRED);
            CommonUtil.jsonResponse(response, JSONUtil.toJsonStr(res));
            return null;
        }
        return claims;
    }

    /**
     * 令牌快过期生成新的令牌并设置到返回头中 客户端在每次的restful请求如果发现有就替换原来的
     */
    public static void refreshTokenHeader(HttpServletResponse response, Claims claims, JwtUserDetails user, Long tokenRefreshSec, Long expiredTtlSec) {

//        System.out.println("====================token过期检查=====================");
//        System.out.println("token过期时间点：" + claims.getExpiration());
//        System.out.println("token刷新时间点：" + new Date(claims.getExpiration().getTime() - tokenRefreshSec * 1000));
//        System.out.println("是否需要刷新了：" + new Date(claims.getExpiration().getTime() - tokenRefreshSec * 1000).before(new Date()));

        if (new Date(claims.getExpiration().getTime() - tokenRefreshSec * 1000).before(new Date())) {
            String jwtToken = generateToken(user, expiredTtlSec);
            response.setHeader(CommonConst.DEFAULT_TOKEN_NAME, jwtToken);
        }
    }

    /**
     * token内容规则
     */
    public static String generateToken(JwtUserDetails user, Long expiredTtlSec) {
        String json = JSONUtil.toJsonStr(user);
        return JwtUtil.createJwtToken(String.valueOf(user.getId()), json, expiredTtlSec * 1000);
    }

    /**
     * 一些默认信息的检查
     *
     * @param user
     */
    public static void checkUserDetails(UserDetails user) {
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        if (!user.isAccountNonLocked()) {
            throw new LockedException("User account is locked");
        }

        if (!user.isEnabled()) {
            throw new DisabledException("User is disabled");
        }

        if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("User account has expired");
        }
    }

    /**
     * 检查密码是否正确
     *
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException
     */
    public static void additionalAuthenticationChecks(UserDetails userDetails, Authentication authentication, PasswordEncoder passwordEncoder) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException("Bad credentials");
        }
        String presentedPassword = authentication.getCredentials().toString();
        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    /**
     * 获取token的方法
     */
    private static String findTokenOnRequest(HttpServletRequest request) {
        String token = request.getHeader(CommonConst.DEFAULT_TOKEN_NAME);
        // header里没有，就从cookie里找
        if (StringUtils.isEmpty(token)) {
            token = CommonUtil.getCookie(CommonConst.DEFAULT_TOKEN_NAME);
        }
        return token;
    }

    public static void setStudentRole(JwtUserDetails userDetails) {
        SysRole role = new SysRole();
        // 手动加入学生角色
        role.setId(CommonConst.STUDENT_ROLE_ID);
        userDetails.setRoles(Collections.singletonList(role));
    }

    /**
     * 密码加密方法
     */
    public static String passwordEncode(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
