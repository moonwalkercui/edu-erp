package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.User;
import com.hzb.erp.common.pojo.dto.StudentParamDTO;
import com.hzb.erp.common.pojo.dto.ChangePasswordDTO;
import com.hzb.erp.common.pojo.vo.UserVO;

/**
 * 家长用户服务类
 */
public interface UserService extends IService<User> {

    /**
     * 查找有没有，否则新建一个
     */
    User existOrCreate(String mobile, String name, String passwordEncode);

    /**
     * 通过手机号找用户
     */
    User getByMobile(String mobile);

    User getByWxAccessId(Long id);

    IPage<UserVO> getList(StudentParamDTO param);

    boolean changPassword(ChangePasswordDTO dto);

    boolean register(String mobile, String passwordEncoded, String name);

    boolean updatePwdByMob(String mobile, String passwordEncode);

    String getWxOpenid(Long id);
}
