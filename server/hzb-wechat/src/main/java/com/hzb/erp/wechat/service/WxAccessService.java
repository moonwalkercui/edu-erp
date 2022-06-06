package com.hzb.erp.wechat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.WxAccess;
import com.hzb.erp.common.pojo.dto.StudentParamDTO;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * <p>
 * 微信登录记录表 服务类
 * </p>
 *
 * @author Ryan
 */
public interface WxAccessService extends IService<WxAccess> {

    WxAccess getByOpenId(String openid);

    /**
     * 处理关注
     */
    Boolean subscribe(WxMpUser userWxInfo);

    /**
     * 处理取消关注
     */
    Boolean unsubscribe(String openid);

    /**
     * 获取或记录登录者
     */
    WxAccess getOrSaveRecord(WxOAuth2UserInfo user);

    /**
    * 分页列表
    * */
    IPage<WxAccess> getList(StudentParamDTO param);
}
