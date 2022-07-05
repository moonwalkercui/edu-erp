package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.pojo.UserParamDTO;
import com.hzb.erp.common.pojo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * com.hzb.erp.common.com.hzb.erp.common.User
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    IPage<UserVO> getList(Page<Object> objectPage, UserParamDTO param);

    String getWxOpenid(Long id);

    User getByOpenid(String openid);
}




