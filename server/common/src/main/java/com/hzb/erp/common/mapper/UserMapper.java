package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.pojo.dto.StudentParamDTO;
import com.hzb.erp.common.pojo.vo.UserVO;
import org.springframework.stereotype.Repository;

/**
 *  com.hzb.erp.common.com.hzb.erp.common.User
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    IPage<UserVO> getList(Page<Object> objectPage, StudentParamDTO param);

    String getWxOpenid(Long id);
}




