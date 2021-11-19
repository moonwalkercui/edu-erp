package com.hzb.erp.security.provider.staff;

import com.hzb.erp.common.entity.Staff;
import com.hzb.erp.common.service.StaffService;
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
public class StaffUserDetailsService implements UserDetailsService {

    @Autowired
    private SecurityUserMapper securityUserMapper;
    @Autowired
    private StaffService staffService;

    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUserDetails userDetails = securityUserMapper.loadStaffByUsername(username);
        if(userDetails != null) {
            userDetails.setIdentity(LoginUserIdentity.STAFF.getCode());
        }
        return userDetails;
    }

    public void storeWxAccessId(String mobile, Long wxAccessId) {
        Staff staff = staffService.getByMobile(mobile);
        if(staff != null) {
            staff.setWxAccessId(wxAccessId);
            staffService.updateById(staff);
        }
    }
}