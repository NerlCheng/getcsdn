package com.getcsdn.Controller;

import java.io.*;
import java.util.*;
import com.getcsdn.ConfigUtil;
import com.getcsdn.entity.ArticleEntity;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

@Controller
public class GetCsdnByUsernameController {
    private static String blogName = "aiming66";
    private static final String PRE_URL = "https://blog.csdn.net/";
    private static final String SUF_URL = "/article/list/";
    public List<ArticleEntity> articleEntitiesList = new ArrayList<>();
    private static final String CONTRACT = ConfigUtil.pro.getProperty("export.CSDN.CONTRACT");

    @Resource
    private com.getcsdn.Services.getCsdnTitleAndUrl getCsdnTitleAndUrl;
    @Resource
    private com.getcsdn.Services.getCsdnArticle getCsdnArticle;
    @Resource
    private com.getcsdn.Services.createDocimpl createDoc;

    @RequestMapping("/getCsdnByUserName")
    public @ResponseBody
    void main(String[] args) {
        try {
            long startTime=System.currentTimeMillis();
//            获取博客的连接和标题
            List<String[]> list = getCsdnTitleAndUrl.getcsdn(PRE_URL, SUF_URL, blogName);
//            获取博客内容
            for (String[] li : list) {
                ArticleEntity articleEntity = getCsdnArticle.getArticleFromUrl(li[0], li[1], blogName);
                if (articleEntity.getCreateDate() != null) {
                    articleEntitiesList.add(articleEntity);
                }
            }
            String templateName = "getcsdn";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("articleEntitiesList", articleEntitiesList);

            // 获取本地模板存储路径
            String templatePath = this.getClass().getClassLoader().getResource("templates").getPath();
            // 判断本地路径是否存在如果不存在则创建
            templateName = templateName + ".ftl";
            createDoc.createDoc( startTime,templatePath, articleEntitiesList,  templateName, blogName+".doc");


        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
