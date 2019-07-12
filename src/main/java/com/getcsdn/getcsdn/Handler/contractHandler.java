package com.getcsdn.getcsdn.Handler;

import com.getcsdn.getcsdn.ConfigUtil;

import javax.annotation.Resource;
import java.io.File;
import java.util.Map;

public class contractHandler {
    private static final String CONTRACT = ConfigUtil.pro.getProperty("export.examPaper.CONTRACT");

    @Resource
    private htmHandler htmlHandler;

    public void contractHandler(String templateName, Map<String,
            Object> paramMap, String pdfName, String htmlName) throws Exception {
        // 获取本地模板存储路径、合同文件存储路径
        String fileUrl = this.getClass().getClassLoader().getResource("templates").getPath();
        String templatePath = fileUrl;
        String contractPath = CONTRACT;
        // 组装html和pdf合同的全路径URL
        String localHtmlUrl = contractPath + htmlName + ".html";
        String localPdfPath = contractPath + "/" + "chengjinpenggetcsdn";
        // 判断本地路径是否存在如果不存在则创建
        File localFile = new File(localPdfPath);
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        String localPdfUrl = localFile + "/" + pdfName + ".pdf";
        templateName = templateName + ".ftl";
        htmlHandler.htmHandler(templatePath, templateName, localHtmlUrl, paramMap);// 生成html合同

//        pdfHandler(localHtmlUrl, localPdfUrl);// 根据html合同生成pdf合同

    }
}
