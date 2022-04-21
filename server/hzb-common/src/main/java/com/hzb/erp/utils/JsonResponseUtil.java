package com.hzb.erp.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import org.apache.poi.ss.formula.functions.T;

/**
 * ClassName: ResponseUtils
 * Description:
 *
 * @author Ryan
 */
public class JsonResponseUtil {

    public static final String SUCCESS_MSG = "提交成功";
    public static final String ERROR = "提交失败";

    /**
     * 调用成功
     */
    public static <T> JsonResponse<T> success() {
        return success(SUCCESS_MSG, null);
    }

    public static <T> JsonResponse<T> data(T data) {
        return success(SUCCESS_MSG, data);
    }

    public static <T> JsonResponse<T> success(String msg) {
        return success(msg, null);
    }

    public static <T> JsonResponse<T> success(String msg, T data) {
        JsonResponse<T> res = new JsonResponse<>();
        res.setData(data);
        res.setMsg(msg);
        return res;
    }

    /**
     * 调用错误
     */
    public static <T> JsonResponse<T> error(String msg) {
        JsonResponse<T> res = new JsonResponse<>();
        res.setErrCode(ResponseCodeEnums.BIZ_ERROR.getCode());
        res.setMsg(msg);
        return res;
    }

    public static <T> JsonResponse<T> error() {
        JsonResponse<T> res = new JsonResponse<>();
        res.setErrCode(ResponseCodeEnums.BIZ_ERROR.getCode());
        res.setMsg(ERROR);
        return res;
    }

    public static <T> JsonResponse<T> error(ResponseCodeEnums responseCodeEnums) {
        return error(responseCodeEnums.getCode(), responseCodeEnums.getMsg());
    }

    public static <T> JsonResponse<T> error(Integer code, String msg) {
        JsonResponse<T> res = new JsonResponse<>();
        res.setErrCode(code);
        res.setMsg(msg);
        return res;
    }

    /**
     * 生成分页
     */
    public static PaginationVO paginate(IPage<?> ipage) {
        PaginationVO res = new PaginationVO();
        res.setRecords(ipage.getRecords());
        res.setTotal(ipage.getTotal());
        res.setPage(ipage.getCurrent());
        res.setPageSize(ipage.getSize());
        res.setPageCount(ipage.getPages());
        return res;
    }

}
