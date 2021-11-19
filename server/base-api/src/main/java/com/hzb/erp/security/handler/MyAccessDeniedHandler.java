package com.hzb.erp.security.handler;


import cn.hutool.json.JSONUtil;
import com.hzb.erp.utils.CommonUtil;
import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import com.hzb.erp.utils.ResponseCodeEnums;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: 权限不足异常处理
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        JsonResponse res = JsonResponseUtil.error(ResponseCodeEnums.FORBIDDEN.getCode(), exception.getMessage());
        CommonUtil.jsonResponse(response, JSONUtil.toJsonStr(res));
    }
}