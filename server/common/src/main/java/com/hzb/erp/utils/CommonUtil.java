package com.hzb.erp.utils;

import com.hzb.erp.common.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author Ryan
 */
public class CommonUtil {
    /**
     * 处理请求参数
     */
    public static void handleValidMessage(BindingResult result) {
        List<FieldError> fieldErrors = result.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            throw new BizException(fieldErrors.get(0).getDefaultMessage());
        }
    }

    /**
     * 获取年份
     */
    public static String getSysYear() {
        Calendar date = Calendar.getInstance();
        return String.valueOf(date.get(Calendar.YEAR));
    }

    /**
     * 将时间转换为时间戳
     */
    public static long dateToTimestamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        return date.getTime();
    }

    /**
     * 将时间戳转换为时间
     */
    public static String timestampToDate(Long s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(s);
        return simpleDateFormat.format(date);
    }

    /**
     * 将字符串转换为DATE
     */
    public static Date strToDate(String str) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.parse(str);
    }

    /**
     * 获取精确到秒的时间戳
     */
    public static int timestampToSec(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.parseInt(timestamp);
    }

    /**
     * json响应
     */
    public static void jsonResponse(HttpServletResponse response, String jsonStr) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonStr);
    }

    /**
     * 请求方式判断
     */
    public static boolean isPost(HttpServletRequest request) {
        return request.getMethod().equals("POST");
    }


    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /**
     * 下划线转驼峰
     */
    public static String lineToHump(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 驼峰转下划线,效率比上面高
     */
    public static String humpToLine(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 检查文件扩展名
     *
     * @param fileName 上传文件名
     * @param ext      扩展名 如 ".jpg,.png.bmp"
     */
    public static Boolean checkFileExt(String fileName, String ext) {
        if (StringUtils.isBlank(fileName)) {
            return false;
        }
        String[] extList = ext.trim().split(",");

        for (String ex : extList) {
            if (fileName.endsWith(ex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查文件扩展名
     *
     * @param cookieName cookie名
     */
    public static String getCookie(String cookieName) {
        HttpServletRequest request = RequestUtil.getRequest();
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }

    /**
     * 生成唯一码
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     * @param version1 版本号1
     * @param version2 版本号2
     * @return int类型 大于0表示version1大，0表示一样大
     */
    public static Integer versionCompare(String version1, String version2) {
        if (version1 == null || version2 == null) {
            return null;
        }
        String[] versionArray1 = version1.split("\\.");
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        //取最小长度值
        int minLength = Math.min(versionArray1.length, versionArray2.length);
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }
}
