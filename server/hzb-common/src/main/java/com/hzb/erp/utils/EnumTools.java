package com.hzb.erp.utils;

import com.hzb.erp.common.enums.BaseEnum;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ryan
 * description 枚举操作
 **/
public class EnumTools {
    /**
     * 显示方法
     *
     * @param c
     * @throws Exception 异常
     */
    public static <T extends BaseEnum> Map<Object, String> fetchForMap(Class<?> c) throws Exception {
        Map<Object, String> res = new LinkedHashMap<>();
        if (!c.isEnum()) {
            return res;
        }
        Method method = c.getDeclaredMethod("values");
        T[] elements = (T[]) method.invoke(null);

        for (T element : elements) {
            res.put(element.getCode(), element.getDist());
        }
        return res;
    }

    /**
     * 通过id来查询枚举的某一个值
     *
     * @param c
     * @param id
     * @return BaseEnum
     * @throws Exception 异常
     */
    public static <T extends BaseEnum> String queryById(Class<T> c, int id) throws Exception {
        //需要加上codeOf的参数类型
        Method method = c.getDeclaredMethod("codeOf", int.class);
        T rejectReasonType = (T) method.invoke(null, id);
        if (rejectReasonType == null) {
            return "";
        }
        StringBuilder reason = new StringBuilder();
        reason.append(rejectReasonType.getDist());
        return reason.toString();

    }

    /**
     * 通过dist找enum
     */
    public static <T extends BaseEnum> T getByDist(String dist, Class<T> enumClass) {
        if (StringUtils.isNotBlank(dist)) {
            for (T each : enumClass.getEnumConstants()) {
                if (dist.equals(each.getDist())) {
                    return each;
                }
            }
        }
        return null;
    }

    /**
     * 通过dist找enum的code
     */
    public static <T extends BaseEnum> Integer getCodeByDist(String dist, Class<T> enumClass) {
        if(StringUtils.isBlank(dist)) {
            return null;
        }
        BaseEnum target = getByDist(dist, enumClass);
        return target == null ? null : target.getCode();
    }

    /**
     * 通过code找enum
     */
    public static <T extends BaseEnum> T getByCode(int code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code == each.getCode()) {
                return each;
            }
        }
        return null;
    }

}