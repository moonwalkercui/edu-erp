package com.hzb.erp.security.provider.user;

import com.hzb.erp.constants.CommonConst;
import com.hzb.erp.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: 用户登录验证拦截器 --  执行顺序在UsernamePasswordAuthenticationFilter 拦截器后
 */
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final boolean postOnly = true;

    /**
     * 拦截逻辑
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !CommonUtil.isPost(request)) {
            throw new AuthenticationServiceException("请求方法有误: " + request.getMethod());
        }
        String username = request.getParameter(CommonConst.STUDENT_LOGIN_USERNAME);
        String password = request.getParameter(CommonConst.STUDENT_LOGIN_PASSWORD);

        username = StringUtils.trimToEmpty(username);
        password = StringUtils.trimToEmpty(password);

        UserAuthenticationToken authenticationToken = new UserAuthenticationToken(username, password);
        authenticationToken.setDetails(new WebAuthenticationDetails(request));

        Authentication authenticatedToken;
        try {
            authenticatedToken = this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
        return authenticatedToken;
    }
}