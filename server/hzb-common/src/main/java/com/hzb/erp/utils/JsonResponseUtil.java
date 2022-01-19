package com.hzb.erp.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hzb.erp.common.pojo.vo.PaginationVO;

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
    public static JsonResponse success() {
        return success(SUCCESS_MSG, null);
    }

    public static <T> JsonResponse data(T data) {
        return success(SUCCESS_MSG, data);
    }

    public static JsonResponse success(String msg) {
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
    public static JsonResponse error(String msg) {
        JsonResponse res = new JsonResponse();
        res.setErrCode(ResponseCodeEnums.BIZ_ERROR.getCode());
        res.setMsg(msg);
        return res;
    }

    public static JsonResponse error() {
        JsonResponse res = new JsonResponse();
        res.setErrCode(ResponseCodeEnums.BIZ_ERROR.getCode());
        res.setMsg(ERROR);
        return res;
    }

    public static JsonResponse error(ResponseCodeEnums responseCodeEnums) {
        return error(responseCodeEnums.getCode(), responseCodeEnums.getMsg());
    }

    public static JsonResponse error(Integer code, String msg) {
        JsonResponse res = new JsonResponse();
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
