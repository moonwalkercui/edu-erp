package com.hzb.erp.security.provider.staff;

import com.hzb.erp.constants.CommonConst;
import com.hzb.erp.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description: 用户登录验证拦截器 -- 执行顺序在UsernamePasswordAuthenticationFilter
 */
public class StaffAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final boolean postOnly = true;

    /**
     * 拦截逻辑
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !CommonUtil.isPost(request)) {
            throw new AuthenticationServiceException("请求方法有误: " + request.getMethod());
        }
        String username = request.getParameter(CommonConst.STAFF_LOGIN_USERNAME);
        String password = request.getParameter(CommonConst.STAFF_LOGIN_PASSWORD);

        username = StringUtils.trimToEmpty(username);
        password = StringUtils.trimToEmpty(password);

        //创建未认证的凭证(etAuthenticated(false)),注意此时凭证中的主体principal为用户名
        StaffAuthenticationToken authenticationToken = new StaffAuthenticationToken(username, password);
        //将认证详情(ip,sessionId)写到凭证
        authenticationToken.setDetails(new WebAuthenticationDetails(request));
        //AuthenticationManager获取受支持的AuthenticationProvider(这里也就是JwtAuthenticationProvider),
        //生成已认证的凭证,此时凭证中的主体为userDetails  --- 这里会委托给AuthenticationProvider实现类来验证
        // 即 跳转到 JwtAuthenticationProvider.authenticate 方法中认证
        Authentication authenticatedToken;
        try {
            authenticatedToken = this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
        return authenticatedToken;
    }
}