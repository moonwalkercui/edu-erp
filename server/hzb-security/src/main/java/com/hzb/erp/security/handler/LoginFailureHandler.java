package com.hzb.erp.security.handler;

import cn.hutool.json.JSONUtil;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.utils.ResponseCodeEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description :  登陆验证失败处理
 */
@Component
@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String msg = "登录失败";
        if (exception instanceof BadCredentialsException ||
                exception instanceof UsernameNotFoundException) {
            msg = "账户或密码输入错误!";
        } else if (exception instanceof LockedException) {
            msg = "账户被锁定，请联系管理员!";
        } else if (exception instanceof CredentialsExpiredException) {
            msg = "密码过期，请联系管理员!";
        } else if (exception instanceof AccountExpiredException) {
            msg = "账户过期，请联系管理员!";
        } else if (exception instanceof DisabledException) {
            msg = "账户被禁用，请联系管理员!";
//        } else if (exception instanceof AuthenticationServiceException && !"Bad credentials".equals(exception.getMessage())) {
//            msg = exception.getMessage();
        }
        JsonResponse res = JsonResponseUtil.error(ResponseCodeEnums.LOGIN_ERROR.getCode(), msg);
        CommonUtil.jsonResponse(response, JSONUtil.toJsonStr(res));
    }
}
