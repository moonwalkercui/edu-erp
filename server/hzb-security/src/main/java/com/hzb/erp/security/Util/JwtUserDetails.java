package com.hzb.erp.security.Util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hzb.erp.common.entity.rbac.SysRole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class JwtUserDetails implements UserDetails {
    private Long id;

    private String name;

    /**
     * mobile 当作用户名
     */
    private String username;

    @JsonIgnore
    private String password;

    private Short state;

    private List<SysRole> roles;

    /**
     * 身份 LoginUserIdentity
     */
    private Integer identity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles == null
                ? new ArrayList<SimpleGrantedAuthority>()
                : roles.stream().map(r -> new SimpleGrantedAuthority(String.valueOf(r.getId()))).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return 1 == state;
    }
}