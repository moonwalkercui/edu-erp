package com.hzb.erp.service;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.hzb.erp.common.enums.BaseEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Service
@Slf4j
public class ImportExportService {
    /**
     * 结果集导出excel
     *
     * @param headerMap 表头映射 eg： Map<String, String> header = new HashMap<String, String>(){{
     *                  put("name", "*姓名"); // 导出模板时，*开头的不能为空，导入时会检测
     *                  put("score", "*分数");
     *                  }};
     */
    public <T> void exportExcel(Map<String, String> headerMap, List<T> dataList, String titleName) {

        if (dataList == null || dataList.size() == 0) {
            throw new BizException("导出数据为空");
        }
        titleName += LocalDate.now().toString();

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        if (response == null) {
            throw new BizException("响应错误");
        }

        //通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();

        //自定义标题名
        for (String key : headerMap.keySet()) {
            writer.addHeaderAlias(key, headerMap.get(key));
        }

        List<Map<String, Object>> data = new ArrayList<>();

        System.out.println("====");
        try {
            for (T item : dataList) {
                Map<String, Object> newMap = new LinkedHashMap<>();
                Map<String, Object> itemMap = objectToMap(item);
                for (String key : headerMap.keySet()) {
                    newMap.put(key, itemMap.get(key));
                }
                data.add(newMap);
            }
            System.out.println(data);
        } catch (IllegalAccessException e) {
            writer.close();
            throw new BizException("数据映射错误:" + e.getMessage());
        }

        //自定义标题名
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(headerMap.size() - 1, titleName);
        //一次性写出内容，使用默认样式，强制输出标题
        writer.write(data, true);

        handleExport(response, writer, titleName);
    }

    private static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if (value instanceof BaseEnum) {
                map.put(fieldName, ((BaseEnum) value).getDist());
            } else {
                map.put(fieldName, value == null ? "" : String.valueOf(value) );
            }
        }
        return map;
    }

    /**
     * map导出excel
     *
     * @param headerMap 表头映射 eg： Map<String, String> header = new HashMap<String, String>(){{
     *                  put("name", "*姓名"); // 导出模板时，*开头的不能为空，导入时会检测
     *                  put("score", "*分数");
     *                  }};
     */
    public void exportExcel(HttpServletResponse response, Map<String, String> headerMap, List<Map<String, Object>> exampleList, String titleName) {

        titleName += LocalDate.now().toString();
        //通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();

        //自定义标题名
        Map<String, Object> emptyRow = new LinkedHashMap<>();
        for (String key : headerMap.keySet()) {
            writer.addHeaderAlias(key, headerMap.get(key));
            emptyRow.put(key, "");
        }
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(headerMap.size() - 1, titleName);

        //一次性写出内容，使用默认样式，强制输出标题
        if (exampleList == null) {
            exampleList = new ArrayList<>();
            exampleList.add(emptyRow);
        }
        writer.write(exampleList, true);

        handleExport(response, writer, titleName);
    }

    private void handleExport(HttpServletResponse response, ExcelWriter writer, String titleName) {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        try {
            String name = URLEncoder.encode(titleName, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xls");
        } catch (UnsupportedEncodingException e) {
            log.error("URL转码异常：" + e.getMessage());
        }
        try {
            writer.flush(response.getOutputStream(), true);
            writer.close();
        } catch (Exception e) {
            log.error("Excel导出IO异常：" + e.getMessage());
        }
    }

    /**
     * 导入返回list
     *
     * @param headerMap 表头映射 eg： Map<String, String> header = new HashMap<String, String>(){{
     *                  put("name", "*姓名"); // *开头的不能为空
     *                  put("score", "*分数");
     *                  }};
     */
    public List<Map<String, Object>> importExcel(MultipartFile file, Map<String, String> headerMap) {

        String fileName = file.getOriginalFilename();
        if (!CommonUtil.checkFileExt(fileName, ".xls,.xlsx")) {
            log.error("请上传扩展名为.xls或.xlsx的文件");
            throw new BizException("请上传扩展名为.xls或.xlsx的文件");
        }

        ExcelReader reader;
        try {
            //得到文件流
            InputStream inputStream = file.getResource().getInputStream();
            reader = ExcelUtil.getReader(inputStream);
        } catch (IOException e) {
            log.error("导入出错：" + e.getMessage());
            throw new BizException("导入出错：" + e.getMessage());
        }
        // hutool读取excel 1：表示表格头所在行，2：从第几行开始读取，2147483647：行的最大值
        // 使用map接收，如果没有设置别名，可以使用实体接收
        List<Map<String, Object>> readAll = reader.read(1, 1, 100000);

        List<Map<String, Object>> res = new ArrayList<>();

        for (Map<String, Object> oneMap : readAll) {
            Map<String, Object> resMap = new HashMap<>();
            for (String key1 : oneMap.keySet()) {
                if (key1.trim().startsWith("*") && StringUtils.isEmpty(oneMap.get(key1).toString())) {
                    throw new BizException(key1 + "列有空值，无法导入!");
                }
//                System.out.println("==== oneMap == ");
//                System.out.println(key1);
//                System.out.println(key1.startsWith("*"));
//                System.out.println(oneMap.get(key1).toString());
//                System.out.println(StringUtils.isEmpty(oneMap.get(key1).toString()));

                for (String key2 : headerMap.keySet()) {
                    if (key1.equals(headerMap.get(key2))) {
                        resMap.put(key2, oneMap.get(key1));
                        break;
                    }
                }
            }
            res.add(resMap);
        }
        System.out.println("==== 导入结果 === ");
        System.out.println(res);
        return res;
    }

    /**
     * 检查重复列
     *
     * @param dataList  数据列表
     * @param fieldName 要检查的列
     */
    public static Boolean isRowNotUnique(List<Map<String, Object>> dataList, String fieldName) {
        Set<Object> set1 = new HashSet<>();
        for (Map<String, Object> m : dataList) {
            set1.add(m.get(fieldName));
        }
        if (dataList.size() != set1.size()) {
            return true;
        }
        return false;
    }

}
