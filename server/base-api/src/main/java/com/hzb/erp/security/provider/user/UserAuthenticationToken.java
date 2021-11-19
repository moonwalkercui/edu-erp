package com.hzb.erp.security.provider.user;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * description: 用户鉴权 ： 保存当前用户的认证信息,如认证状态,用户名密码,拥有的权限等
 */
public class UserAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 登录用户信息
     */
    private final Object principal;
    /**
     * 密码
     */
    private Object credentials;

    /**
     * 创建一个未认证的授权令牌,
     * 这时传入的principal是用户名
     */
    public UserAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    /**
     * 创建一个已认证的授权令牌,如注释中说的那样,这个方法应该由AuthenticationProvider来调用
     * 也就是我们写的JwtAuthenticationProvider,有它完成认证后再调用这个方法,
     * 这时传入的principal为从userService中查出的UserDetails
     *
     * @param principal
     * @param credentials
     * @param authorities
     */
    public UserAuthenticationToken(Object principal, Object credentials,
                                   Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

}