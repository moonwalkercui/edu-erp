package com.hzb.erp.api.base.component;

import com.hzb.erp.api.base.annotation.Log;
import com.hzb.erp.common.entity.SysLog;
import com.hzb.erp.common.service.SysLogService;
import com.hzb.erp.configuration.SystemConfig;
import com.hzb.erp.utils.RequestUtil;
import com.hzb.erp.api.base.service.UserAuthService;
import com.hzb.erp.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 定义切面
 *
 * @author Ryan
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private SystemConfig systemConfig;

    /*
     * web层切点
     * 1. @Pointcut("execution(public * com.hyh.web.*.*(..))")  web层的所有方法
     * 2. @Pointcut("@annotation(com.hyh.annotation.Log)")      Log注解标注的方法
     */

    /**
     * 环绕通知
     */
    @Around("@annotation(com.hzb.erp.api.base.annotation.Log)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = RequestUtil.getRequest();

        handleBlackList(request);

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeCost = System.currentTimeMillis() - start;
        Log logAnnotation = getAnnotation(joinPoint);

        // 添加日志记录
        Long userId = UserAuthService.getCurrentUserId();
        SysLog olog = new SysLog();
        if (logAnnotation.isStaff()) {
            olog.setOperator(userId);
        } else {
            olog.setStudentId(userId);
        }
        olog.setInfo(logAnnotation.description());
        olog.setType(logAnnotation.type());
        olog.setPath(request.getRequestURI());
        olog.setUrl(String.valueOf(request.getRequestURL()));
        olog.setParam(getParams(joinPoint).toString());
        olog.setMethod(request.getMethod());
        olog.setIp(IpUtil.getIpAddr(request));
        olog.setBrowserName(IpUtil.getBrowserName(request));
        olog.setBrowserVer(IpUtil.getBrowserVersion(request));
        olog.setOsName(IpUtil.getOsName(request));
        olog.setAddTime(LocalDateTime.now());
        olog.setTimeCost(timeCost);
        sysLogService.addOne(olog);
        return result;
    }

    /**
     * 获取方法上的注解
     */
    private Log getAnnotation(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        return method.getAnnotation(Log.class);
    }

    /**
     * 获取参数
     */
    private Object getParams(ProceedingJoinPoint joinPoint) {
        // 参数名
        String[] paramNames = getMethodSignature(joinPoint).getParameterNames();
        // 参数值
        Object[] paramValues = joinPoint.getArgs();
        // 存储参数
        Map<String, Object> params = new LinkedHashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];
            // 文件对象以文件名作为参数值
            if (value instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) value;
                value = file.getOriginalFilename();
            }
            params.put(paramNames[i], value);
        }
        return params;
    }

    private MethodSignature getMethodSignature(ProceedingJoinPoint joinPoint) {
        return (MethodSignature) joinPoint.getSignature();
    }

    /**
     * 处理黑名单
     */
    private void handleBlackList(HttpServletRequest request) {

        String ip = IpUtil.getIpAddr(request);
        List<String> blackList = systemConfig.getBlackList();
        if (blackList == null || blackList.size() == 0) {
            return;
        }

        for (String bl : blackList) {
            if (bl.equals(ip)) {
                throw new RuntimeException("被你整坏了！");
            }
        }

    }
}
