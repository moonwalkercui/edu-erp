package com.hzb.erp.security.provider;

import com.hzb.erp.security.Util.JwtUserDetails;
import com.hzb.erp.security.Util.SecurityUtils;
import com.hzb.erp.security.provider.user.UserAuthenticationToken;
import com.hzb.erp.security.provider.user.UserUserDetailsService;
import com.hzb.erp.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * description: 用户角色校验具体实现
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserUserDetailsService userDetailsService;

    /**
     * 鉴权具体逻辑 登录时路过
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtUserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        SecurityUtils.checkUserDetails(userDetails);
        // 手动加入学生角色
        SecurityUtils.setStudentRole(userDetails);

        String wxAccessId = CommonUtil.getCookie("wxaccess");
        if (StringUtils.isNotBlank(wxAccessId)) {
            userDetailsService.storeWxAccessId(userDetails.getId(), Long.valueOf(wxAccessId));
        }

        //转换authentication 认证时会先调用support方法,受支持才会调用,所以能强转
        UserAuthenticationToken authenticationToken = (UserAuthenticationToken) authentication;
        // 用户名密码校验 具体逻辑
        SecurityUtils.additionalAuthenticationChecks(userDetails, authentication, passwordEncoder());
        //构造已认证的authentication
        UserAuthenticationToken authenticatedToken = new UserAuthenticationToken(userDetails, authenticationToken.getCredentials(), userDetails.getAuthorities());
        authenticatedToken.setDetails(authenticationToken.getDetails());
        return authenticatedToken;
    }

    /**
     * 是否支持当前类
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UserAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}