package com.hzb.erp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    private static Properties loadPropertiesByFileName(String fileName) {
        Properties prop = new Properties();
        InputStream inputStream = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            System.out.println("配置文件未找到");
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * 是否是线上环境
     */
    public static boolean isProd() {
        Properties properties = loadPropertiesByFileName("application.yml");
        return "prod".equals(properties.get("active").toString());
    }

    public static boolean isDev() {
        Properties properties = loadPropertiesByFileName("application.yml");
        return "dev".equals(properties.get("active").toString());
    }

    public static String get(String propertyName) {
        Properties properties = loadPropertiesByFileName("application.yml");
        String config = properties.getProperty("active");
        String fileName = "application-" + config + ".yml";
        return loadPropertiesByFileName(fileName).get(propertyName).toString();
    }


}
