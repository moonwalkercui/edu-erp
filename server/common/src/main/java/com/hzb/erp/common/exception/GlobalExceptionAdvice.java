package com.hzb.erp.common.exception;

import com.hzb.erp.utils.JsonResponse;
import com.hzb.erp.utils.JsonResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

//    @ExceptionHandler(value = Exception.class)
//    public void handleHttpException(HttpServletRequest req, Exception ex){
//        System.out.println("发生异常了:" + ex.getMessage());
//        System.out.println(ex.toString());
//    }
//    @ExceptionHandler(value =NullPointerException.class)
//    @ResponseBody
//    public JsonResponse exceptionHandler(HttpServletRequest req, NullPointerException e){
//        return JsonResponseUtils.error(e.getMessage());
//    }

    /**
     * 处理BizException异常
     *
     * @param e 异常
     * @return Response
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public JsonResponse bizHandler(HttpServletRequest req, BizException e) {
        log.error(e.getMessage());
        return JsonResponseUtil.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理sql异常
     *
     * @param e 异常
     * @return Response
     */
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public JsonResponse sqlExceptionHandler(HttpServletRequest req, Exception e) {
        log.error(e.getMessage());
        return JsonResponseUtil.error(e.getMessage());
    }

    /**
     * 上传异常
     *
     * @param e 异常
     * @return Response
     */
    @ExceptionHandler(value = FileUploadException.class)
    @ResponseBody
    public JsonResponse uploadExceptionHandler(HttpServletRequest req, Exception e) {
        log.error( e.getMessage());
        return JsonResponseUtil.error(e.getMessage());
    }
}
