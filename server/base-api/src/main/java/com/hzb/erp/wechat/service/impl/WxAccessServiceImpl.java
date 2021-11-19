package com.hzb.erp.wechat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.WxAccess;
import com.hzb.erp.common.enums.GenderEnum;
import com.hzb.erp.common.mapper.WxAccessMapper;
import com.hzb.erp.utils.EnumTools;
import com.hzb.erp.wechat.service.WxAccessService;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 微信登录记录表 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class WxAccessServiceImpl extends ServiceImpl<WxAccessMapper, WxAccess> implements WxAccessService {

    @Override
    public WxAccess getOrSaveRecord(WxOAuth2UserInfo user) {
        WxAccess access = getByOpenId(user.getOpenid());
        if (access == null) {
            access = new WxAccess();
            access.setOpenid(user.getOpenid());
            access.setUnionid(user.getUnionId());
            access.setNickname(user.getNickname());
            access.setAddTime(LocalDateTime.now());
            access.setLatestLogin(LocalDateTime.now());
            access.setUnsubscribe(false);
            access.setHeadImg(user.getHeadImgUrl());
            access.setGender(EnumTools.getByCode(user.getSex(), GenderEnum.class));
            access.setCity(user.getCity());
            access.setProvince(user.getProvince());
            access.setCountry(user.getCountry());
            save(access);
        } else {
            access.setNickname(user.getNickname());
            access.setHeadImg(user.getHeadImgUrl());
            access.setGender(EnumTools.getByCode(user.getSex(), GenderEnum.class));
            access.setCity(user.getCity());
            access.setProvince(user.getProvince());
            access.setCountry(user.getCountry());
            access.setLatestLogin(LocalDateTime.now());
            updateById(access);
        }
        return access;
    }

    /**
     * 处理关注
     */
    @Override
    public Boolean subscribe(WxMpUser wxUser) {
        WxAccess access = getByOpenId(wxUser.getOpenId());
        if (access == null) {
            access = new WxAccess();
            access.setOpenid(wxUser.getOpenId());
            access.setUnionid(wxUser.getUnionId());
            access.setNickname(wxUser.getNickname());
            access.setAddTime(LocalDateTime.now());
            access.setUnsubscribe(false);
            access.setHeadImg(wxUser.getHeadImgUrl());
            access.setGender(EnumTools.getByCode(wxUser.getSex(), GenderEnum.class));
            access.setCity(wxUser.getCity());
            access.setProvince(wxUser.getProvince());
            access.setCountry(wxUser.getCountry());
            return this.save(access);
        } else {
            access.setUnsubscribe(false);
            access.setUnsubTime(null);
            return this.updateById(access);
        }
    }


    @Override
    public WxAccess getByOpenId(String openid) {
        QueryWrapper<WxAccess> qw = new QueryWrapper<>();
        qw.eq("openid", openid);
        return getOne(qw);
    }

    @Override
    public Boolean unsubscribe(String openid) {
        WxAccess user = getByOpenId(openid);
        if (user == null) {
            return false;
        }
        user.setUnsubscribe(true);
        user.setUnsubTime(LocalDateTime.now());
        return updateById(user);
    }

}
