//package com.hzb.erp.context;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class ThreadContextHolder {
//    private static ThreadLocal<HttpServletRequest> requestThreadLocalHolder = new ThreadLocal();
//    private static ThreadLocal<HttpServletResponse> responseThreadLocalHolder = new ThreadLocal();
//
//    public ThreadContextHolder() {
//    }
//
//    public static void setHttpRequest(HttpServletRequest request) {
//        requestThreadLocalHolder.set(request);
//    }
//
//    public static void setHttpResponse(HttpServletResponse response) {
//        responseThreadLocalHolder.set(response);
//    }
//
//    public static void remove() {
//        requestThreadLocalHolder.remove();
//        responseThreadLocalHolder.remove();
//    }
//
//    public static HttpServletResponse getHttpResponse() {
//        return (HttpServletResponse)responseThreadLocalHolder.get();
//    }
//
//    public static HttpServletRequest getHttpRequest() {
//        return (HttpServletRequest)requestThreadLocalHolder.get();
//    }
//}
