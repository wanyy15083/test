package com.test.utils;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.File;
import java.util.Properties;

public class TemplateUtil {

    /**
     * 根据模板生成文件
     *
     * @param inputFilePath 模板路径
     * @param outputFilePath  输出文件路径
     * @param engine
     * @throws Exception
     */
    public static void generate(String inputFilePath, String outputFilePath, TemplateEngine engine) throws Exception {
        try {
            Properties properties = new Properties();
            FileTemplateResolver templateResolver = new FileTemplateResolver();
            templateResolver.setTemplateMode(TemplateMode.HTML);
            templateResolver.setPrefix("/template/");
            templateResolver.setSuffix(".html");
            templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
            templateResolver.setCacheable(true);
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);
//            properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, getPath(inputFilePath));
//            Velocity.init(properties);
//            //VelocityEngine engine = new VelocityEngine();
//            Template template = Velocity.getTemplate(getFile(inputFilePath), "utf-8");
            File outputFile = new File(outputFilePath);
            FileWriterWithEncoding writer = new FileWriterWithEncoding(outputFile, "utf-8");
//            template.merge(context, writer);
            writer.close();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 根据文件绝对路径获取目录
     *
     * @param filePath
     * @return
     */
    public static String getPath(String filePath) {
        String path = "";
        if (StringUtils.isNotBlank(filePath)) {
            path = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }
        return path;
    }

    /**
     * 根据文件绝对路径获取文件
     *
     * @param filePath
     * @return
     */
    public static String getFile(String filePath) {
        String file = "";
        if (StringUtils.isNotBlank(filePath)) {
            file = filePath.substring(filePath.lastIndexOf("/") + 1);
        }
        return file;
    }

}
