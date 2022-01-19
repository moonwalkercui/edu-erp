package com.hzb.erp.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p> 请求响应获取工具 </p>
 *
 * @author Ryan 541720500@qq.com
 */
public class RequestUtil {

    /**
     * 获取request， 注：在子线程中会有null异常，需要再启动类加一个：
     * Bean：
     * public RequestContextListener requestContextListener(){
     * return new RequestContextListener();
     * }
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getResponse();
    }

    public static String getDomain() {
        HttpServletRequest request = getRequest();
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    public static String getOrigin() {
        HttpServletRequest request = getRequest();
        return request.getHeader("Origin");
    }

    public static String getCookieValue(String cookieName) {
        if (StringUtils.isBlank(cookieName)) {
            return null;
        }
        HttpServletRequest request = getRequest();
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

}
