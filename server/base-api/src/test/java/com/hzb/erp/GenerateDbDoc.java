//package com.hzb.erp;
//import cn.smallbun.screw.core.Configuration;
//import cn.smallbun.screw.core.engine.EngineConfig;
//import cn.smallbun.screw.core.engine.EngineFileType;
//import cn.smallbun.screw.core.engine.EngineTemplateType;
//import cn.smallbun.screw.core.execute.DocumentationExecute;
//import cn.smallbun.screw.core.process.ProcessConfig;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.ApplicationContext;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//
//@SpringBootTest
//class GenerateDbDoc {
//
//    @Autowired
//    ApplicationContext applicationContext;
//
//    @Test
//    void contextLoads() {
//        DataSource dataSourceMysql = applicationContext.getBean(DataSource.class);
//
//        // 生成文件配置
//        EngineConfig engineConfig = EngineConfig.builder()
//                // 生成文件路径(可自定义)
//                .fileOutputDir("C:\\")
//                // 打开目录
//                .openOutputDir(true)
//                //生成文件名(可自定义)
//                .fileName("tablefile")
//                // 文件类型 .HTML .WORD .MARKDOWN
//                .fileType(EngineFileType.WORD)
//                // 生成模板实现
//                .produceType(EngineTemplateType.freemarker)
//                .build();
//
//        // 生成文档配置（包含以下自定义版本号、描述等配置连接）
//        Configuration config = Configuration.builder()
//                .version("1.0.1")   //可自定义
//                .description("某某数据库中文文档")    //可自定义
//                .dataSource(dataSourceMysql)
//                .engineConfig(engineConfig)
//                .produceConfig(getProcessConfig())
//                .build();
//
//        // 执行生成
//        new DocumentationExecute(config).execute();
//    }
//
//
//    /**
//     * 配置想要生成的表+ 配置想要忽略的表
//     * @return 生成表配置
//     */
//    public static ProcessConfig getProcessConfig(){
//        // 忽略表名
////        List<String> ignoreTableName = Arrays.asList("aa","test_group");
//
//        // 忽略表前缀，如忽略a开头的数据库表
////        List<String> ignorePrefix = Arrays.asList("T_");    //,"t"
//
//        // 忽略表后缀
////        List<String> ignoreSuffix = Arrays.asList("_test","czb_");
//
//        return ProcessConfig.builder()
//                //根据名称指定表生成
//                .designatedTableName(new ArrayList<>())
//                //根据表前缀生成
//                .designatedTablePrefix(new ArrayList<>())
//                //根据表后缀生成
//                .designatedTableSuffix(new ArrayList<>())
//                //忽略表名
////                .ignoreTableName(ignoreTableName)
//                //忽略表前缀
////                .ignoreTablePrefix(ignorePrefix)
//                .build();
//        //忽略表后缀
////                .ignoreTableSuffix(ignoreSuffix).build();
//    }
//
//}
