package com.hzb.erp.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.security.Util.JwtUserDetails;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * SecurityMapper 接口
 * </p>
 *
 * @author 541720500@qq.com
 */
@Repository
@Mapper
public interface SecurityUserMapper extends BaseMapper<Object> {
    /**
     * security 登录验证
     */
    JwtUserDetails loadStaffByUsername(String username);

    JwtUserDetails loadUserByUsername(String username);
}
