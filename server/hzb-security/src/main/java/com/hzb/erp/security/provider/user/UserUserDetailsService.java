package com.hzb.erp.security.provider.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzb.erp.common.entity.User;
import com.hzb.erp.common.mapper.UserMapper;
import com.hzb.erp.security.mapper.SecurityUserMapper;
import com.hzb.erp.security.Util.JwtUserDetails;
import com.hzb.erp.security.Enums.LoginUserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * description:
 */
@Service
public class UserUserDetailsService implements UserDetailsService {

    @Autowired
    private SecurityUserMapper securityUserMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUserDetails userDetails = securityUserMapper.loadUserByUsername(username);
        if (userDetails != null) {
            userDetails.setIdentity(LoginUserIdentity.USER.getCode());
        }
        return userDetails;
    }

    public void storeWxAccessId(Long uid, Long wxAccessId) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("id", uid);
        User user = userMapper.selectOne(qw);
        if (user != null) {
            user.setWxAccessId(wxAccessId);
            userMapper.updateById(user);
        }
    }
}