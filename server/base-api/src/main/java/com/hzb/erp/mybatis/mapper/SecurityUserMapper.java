package com.hzb.erp.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.security.Util.JwtUserDetails;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * SecurityMapper 接口
 * </p>
 *
 * @author 541720500@qq.com
 */
@Repository
public interface SecurityUserMapper extends BaseMapper {
    /**
     * security 登录验证
     */
    JwtUserDetails loadStaffByUsername(String username);
    JwtUserDetails loadUserByUsername(String username);
}
