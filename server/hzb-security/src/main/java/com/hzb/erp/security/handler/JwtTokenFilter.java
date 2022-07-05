package com.hzb.erp.security.handler;

import cn.hutool.json.JSONUtil;
import com.hzb.erp.configuration.SystemConfig;
import com.hzb.erp.constants.CommonConst;
import com.hzb.erp.security.Util.JwtUserDetails;
import com.hzb.erp.security.Util.SecurityUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: Token有效性验证拦截器
 */
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Claims claims = SecurityUtils.checkTokenAtFilterInternal(request, response);
        if (claims == null) {
            return;
        }

        // 距离快过期多久刷新令牌
        Long tokenRefreshSec = SystemConfig.getJwtTokenRefreshSec();
        // token过期时间
        Long expiredTtlSec = SystemConfig.getJwtExpiredTtlSec();

        JwtUserDetails user = JSONUtil.toBean(claims.get(CommonConst.JWT_CLAIMS_KEY, String.class), JwtUserDetails.class);
        SecurityUtils.refreshTokenHeader(response, claims, user, tokenRefreshSec, expiredTtlSec);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}