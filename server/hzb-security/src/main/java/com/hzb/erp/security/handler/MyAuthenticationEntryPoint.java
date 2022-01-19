package com.hzb.erp.security.handler;

import cn.hutool.json.JSONUtil;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.utils.ResponseCodeEnums;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description:  用户权限不足处理
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        JsonResponse res = JsonResponseUtil.error(ResponseCodeEnums.FORBIDDEN.getCode(), ResponseCodeEnums.FORBIDDEN.getMsg() + ":" + authException.getMessage());
        CommonUtil.jsonResponse(response, JSONUtil.toJsonStr(res));
    }
}