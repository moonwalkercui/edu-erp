package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.constants.CommonConst;
import com.hzb.erp.common.entity.User;
import com.hzb.erp.common.entity.WxAccess;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.UserMapper;
import com.hzb.erp.common.pojo.dto.StudentParamDTO;
import com.hzb.erp.common.pojo.dto.ChangePasswordDTO;
import com.hzb.erp.common.pojo.dto.WxAccessBindDTO;
import com.hzb.erp.common.pojo.vo.UserVO;
import com.hzb.erp.common.service.UserService;
import com.hzb.erp.utils.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 家长用户实现类
 *
 * @author Ryan
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User existOrCreate(String mobile, String name, String passwordEncode) {
        User user = getByMobile(mobile);
        if (user == null) {
            user = new User();
            user.setName(name);
            user.setMobile(mobile);
            user.setState(true);
            user.setAddTime(LocalDateTime.now());
            if (StringUtils.isNotBlank(passwordEncode)) {
                user.setPassword(passwordEncode);
            }
            save(user);
        }
        return user;
    }

    @Override
    public User getByMobile(String mobile) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("mobile", mobile);
        return getOne(qw);
    }

    @Override
    public User getByWxAccessId(Long id) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("wx_access_id", id).orderByDesc("latest_login_time").last("limit 1");
        return getOne(qw);
    }

    @Override
    public IPage<UserVO> getList(StudentParamDTO param) {
        return baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public boolean changPassword(ChangePasswordDTO dto) {

        if (dto.getId() == null) {
            throw new BizException("缺少参数");
        }
        if (StringUtils.isBlank(dto.getPassword())) {
            throw new BizException("缺少密码");
        }
        if (dto.getPassword().length() <= 3) {
            throw new BizException("密码过短!");
        }
        if (StringUtils.isBlank(dto.getPasswordEncode())) {
            throw new BizException("密码未加密无法保存");
        }
        User user = getById(dto.getId());
        user.setPassword(dto.getPasswordEncode());
        return updateById(user);
    }

    @Override
    public boolean updatePwdByMob(String mobile, String passwordEncode) {
        User byMobile = getByMobile(mobile);
        if (byMobile == null) {
            throw new BizException("手机号错误");
        }
        byMobile.setPassword(passwordEncode);
        return updateById(byMobile);
    }

    @Override
    public String getWxOpenid(Long id) {
        return baseMapper.getWxOpenid(id);
    }

    @Override
    public boolean bindWeixin(WxAccessBindDTO dto) {
        User bindedUser = getByWxAccessId(dto.getWxAccessId());
        if(bindedUser!=null) {
            throw new BizException("该微信已经被其他账号绑定："+bindedUser.getName());
        }
        User user = this.baseMapper.selectById(dto.getUserId());
        if(user == null) {
            throw new BizException("未知用户");
        }
        user.setWxAccessId(dto.getWxAccessId());
        return this.baseMapper.updateById(user) > 0;
    }

    @Override
    public boolean unbindWeixin(Long userId) {
        User user = this.baseMapper.selectById(userId);
        user.setWxAccessId(null);
        return this.baseMapper.updateById(user) > 0;
    }

    @Override
    public boolean register(String mobile, String passwordEncoded, String name) {
        User byMobile = getByMobile(mobile);
        if (byMobile != null) {
            throw new BizException("手机号码已存在");
        }
        User user = new User();
        user.setMobile(mobile);
        user.setName(name);
        user.setPassword(passwordEncoded);
        user.setAddTime(LocalDateTime.now());
        user.setState(true);

        String wxAccessId = RequestUtil.getCookieValue(CommonConst.WX_ACCESS_ID);
        user.setWxAccessId(wxAccessId == null ? null : Long.parseLong(wxAccessId));

        return save(user);
    }

}




