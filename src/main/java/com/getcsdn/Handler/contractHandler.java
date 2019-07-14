package com.getcsdn.Handler;

import com.getcsdn.ConfigUtil;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;
import com.getcsdn.Services.createDoc;
import com.getcsdn.entity.ArticleEntity;
import org.springframework.stereotype.Service;

@Service
public class contractHandler {
    private static final String CONTRACT = ConfigUtil.pro.getProperty("export.examPaper.CONTRACT");

    @Resource
    private createDoc createDoc;

    public void contractHandler(String templateName, List<ArticleEntity> articleEntitiesList) throws Exception {
        // 获取本地模板存储路径、合同文件存储路径
        String fileUrl = this.getClass().getClassLoader().getResource("templates").getPath();
        String templatePath = fileUrl;
        String contractPath = CONTRACT;
        String localPdfPath = contractPath + "/" + "chengjinpenggetcsdn";
        // 判断本地路径是否存在如果不存在则创建
        File localFile = new File(localPdfPath);
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        templateName = templateName + ".ftl";
        createDoc.createDoc(contractPath, templatePath, articleEntitiesList,  templateName, "chengjinpengcreate.doc");

    }
}
