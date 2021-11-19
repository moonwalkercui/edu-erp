package com.hzb.erp.security.provider.user;

import com.hzb.erp.mybatis.mapper.SecurityUserMapper;
import com.hzb.erp.security.Util.JwtUserDetails;
import com.hzb.erp.utils.Enums.LoginUserIdentity;
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

    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUserDetails userDetails = securityUserMapper.loadUserByUsername(username);
        if(userDetails != null) {
            userDetails.setIdentity(LoginUserIdentity.USER.getCode());
        }
        return userDetails;
    }

    public void storeWxAccessId(String mobile, Long wxAccessId) {
//        User user = userService.getByMobile(mobile);
//        if (user != null) {
//            user.setWxAccessId(wxAccessId);
//            studentService.updateById(student);
//        }
    }
}