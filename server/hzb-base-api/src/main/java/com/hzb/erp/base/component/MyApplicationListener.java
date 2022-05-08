package com.hzb.erp.base.component;

import com.hzb.erp.common.service.SysLogService;
import com.hzb.erp.common.service.StaffService;
import com.hzb.erp.service.cache.LoginLockCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * <p> </p>
 *
 * @author Ryan 541720500@qq.com
 */
@Component
@Slf4j
public class MyApplicationListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private StaffService staffService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        Object userName = event.getAuthentication().getPrincipal();
        Object credentials = event.getAuthentication().getCredentials();
        log.info("登录错误，USERNAME [" + userName + "]" + "，using PASSWORD [" + credentials + "]");
        LoginLockCache.fail(userName.toString());
    }
}