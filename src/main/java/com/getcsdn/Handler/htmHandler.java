package com.getcsdn.Handler;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;
@Service
public class htmHandler {
    /**
     * @Description: 由ftl模板生成HTML文件
     * @Param:templatePath 模板路径
     * @param:templateName 模板名称
     * @param:htmUrl html的URL
     * @param:paramMap 存放参数的map
     * @return: 4:13 PM
     * @Author: 杨光彩
     * @Date: 5/16/2019
     */
    public   void htmHandler(String templatePath, String templateName,
                                   String htmUrl, Map<String, Object> paramMap) throws Exception {
        Configuration cfg = new Configuration();
        cfg.setDefaultEncoding("UTF-8");
        cfg.setDirectoryForTemplateLoading(new File(templatePath));
        Template template = cfg.getTemplate(templateName);
        File outHtmFile = new File(htmUrl);
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outHtmFile), "utf-8"));
        template.process(paramMap, out);
        out.close();
    }
}
