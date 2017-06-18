package com.test.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.thymeleaf.context.Context;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码生成类
 */
public class MybatisGeneratorUtil {

    // generatorConfig模板路径
    private static String generatorConfig_vm = "/template/generatorConfig.html";
    // Service模板路径
    private static String service_vm = "/template/Service.html";
    // ServiceMock模板路径
    private static String serviceMock_vm = "/template/ServiceMock.html";
    // ServiceImpl模板路径
    private static String serviceImpl_vm = "/template/ServiceImpl.html";

    /**
     * 根据模板生成generatorConfig.xml文件
     *
     * @param jdbc_driver   驱动路径
     * @param jdbc_url      链接
     * @param jdbc_username 帐号
     * @param jdbc_password 密码
     * @param module        项目模块
     * @param database      数据库
     * @param table_prefix  表前缀
     * @param package_name  包名
     */
    public static void generator(
            String jdbc_driver,
            String jdbc_url,
            String jdbc_username,
            String jdbc_password,
            String module,
            String database,
            String table_prefix,
            String package_name,
            Map<String, String> last_insert_id_tables) throws Exception {

        generatorConfig_vm = MybatisGeneratorUtil.class.getResource(generatorConfig_vm).getPath().replaceFirst("/", "");
//        service_vm = MybatisGeneratorUtil.class.getResource(service_vm).getPath().replaceFirst("/", "");
//        serviceMock_vm = MybatisGeneratorUtil.class.getResource(serviceMock_vm).getPath().replaceFirst("/", "");
//        serviceImpl_vm = MybatisGeneratorUtil.class.getResource(serviceImpl_vm).getPath().replaceFirst("/", "");

        String targetProject = module;
        String module_path = module + "/src/main/resources/generatorConfig.xml";
        String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "' AND table_name LIKE '" + table_prefix + "_%';";

        System.out.println("========== 开始生成generatorConfig.xml文件 ==========");
        List<Map<String, Object>> tables = new ArrayList<>();
        try {
            Context context = new Context();
            Map<String, Object> table;

            // 查询定制前缀项目的所有表
            JdbcUtil jdbcUtil = new JdbcUtil(jdbc_driver, jdbc_url, jdbc_username, jdbc_password);
            List<Map> result = jdbcUtil.selectByParams(sql, null);
            for (Map map : result) {
                System.out.println(map.get("TABLE_NAME"));
                table = new HashMap<>();
                table.put("table_name", map.get("TABLE_NAME"));
                table.put("model_name", lineToHump(String.valueOf(map.get("TABLE_NAME"))));
                tables.add(table);
            }
            jdbcUtil.release();

            String targetProject_sqlMap = module;
            context.setVariable("tables", tables);
            context.setVariable("generator_javaModelGenerator_targetPackage", package_name + ".dao.model");
            context.setVariable("generator_sqlMapGenerator_targetPackage", package_name + ".dao.mapper");
            context.setVariable("generator_javaClientGenerator_targetPackage", package_name + ".dao.mapper");
            context.setVariable("targetProject", targetProject);
            context.setVariable("targetProject_sqlMap", targetProject_sqlMap);
            context.setVariable("generator_jdbc_password", jdbc_password);
            context.setVariable("last_insert_id_tables", last_insert_id_tables);
            TemplateUtil.generate(generatorConfig_vm, module_path, context);
            // 删除旧代码
            deleteDir(new File(targetProject + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/dao/model"));
            deleteDir(new File(targetProject + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/dao/mapper"));
            deleteDir(new File(targetProject_sqlMap + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/dao/mapper"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("========== 结束生成generatorConfig.xml文件 ==========");

        System.out.println("========== 开始运行MybatisGenerator ==========");
        List<String> warnings = new ArrayList<>();
        File configFile = new File(module_path);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            System.out.println(warning);
        }
        System.out.println("========== 结束运行MybatisGenerator ==========");

        System.out.println("========== 开始生成Service ==========");
        String ctime = new SimpleDateFormat("yyyy/M/d").format(new Date());
        String servicePath = module + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/service";
        String serviceImplPath = module + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/service/impl";
        for (int i = 0; i < tables.size(); i++) {
            String model = lineToHump(String.valueOf(tables.get(i).get("table_name")));
            String service = servicePath + "/" + model + "Service.java";
            String serviceMock = servicePath + "/" + model + "ServiceMock.java";
            String serviceImpl = serviceImplPath + "/" + model + "ServiceImpl.java";
            // 生成service
//            File serviceFile = new File(service);
//            if (!serviceFile.exists()) {
//                Context context = new Context();
//                context.setVariable("package_name", package_name);
//                context.setVariable("model", model);
//                context.setVariable("ctime", ctime);
//                TemplateUtil.generate(service_vm, service, context);
//                System.out.println(service);
//            }
            // 生成serviceMock
//            File serviceMockFile = new File(serviceMock);
//            if (!serviceMockFile.exists()) {
//                Context context = new Context();
//                context.setVariable("package_name", package_name);
//                context.setVariable("model", model);
//                context.setVariable("ctime", ctime);
//                TemplateUtil.generate(serviceMock_vm, serviceMock, context);
//                System.out.println(serviceMock);
//            }
            // 生成serviceImpl
//            File serviceImplFile = new File(serviceImpl);
//            if (!serviceImplFile.exists()) {
//                Context context = new Context();
//                context.setVariable("package_name", package_name);
//                context.setVariable("model", model);
//                context.setVariable("mapper", toLowerCaseFirstOne(model));
//                context.setVariable("ctime", ctime);
//                TemplateUtil.generate(serviceImpl_vm, serviceImpl, context);
//                System.out.println(serviceImpl);
//            }
        }
        System.out.println("========== 结束生成Service ==========");

        System.out.println("========== 开始生成Controller ==========");
        System.out.println("========== 结束生成Controller ==========");
    }

    // 递归删除非空文件夹
    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteDir(files[i]);
            }
        }
        dir.delete();
    }

    public static String lineToHump(String str) {
        if (null == str || "".equals(str)) {
            return str;
        }
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);

        str = sb.toString();
        str = str.substring(0, 1).toUpperCase() + str.substring(1);

        return str;
    }

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (StringUtils.isBlank(s)) {
            return s;
        }
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }


    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

}
