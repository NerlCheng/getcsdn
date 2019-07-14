package com.getcsdn.Services.impl;

import com.alibaba.fastjson.JSON;
import com.getcsdn.Services.createDoc;
import com.getcsdn.entity.ArticleEntity;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class createDocimpl implements createDoc {
    public String createDoc(String path, String templatePath, List<ArticleEntity> articleEntitiesList, String templateName, String docName) throws IOException {
        long startTime=System.currentTimeMillis();
        System.out.println("生成word开始。。。");
        Configuration configurationc = new Configuration(Configuration.VERSION_2_3_20);
        //设置模板编码格式
        configurationc.setDefaultEncoding("utf-8");
//         设置模板加载路径
        configurationc.setDirectoryForTemplateLoading(new File(templatePath));
//        Sets how errors will appear.
        configurationc.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Template template=null;
        String filepath=null;
        String htmlpath=null;
        try{
            //获取模板设置编码类型
            template=configurationc.getTemplate(templateName,"UTF-8");
            //设置生成word文件的存放路径
            filepath = path + "export";
            File file=new File(filepath);
            if(!file.exists()){
//                file.createNewFile();
                file.mkdirs();
            }
            filepath+=docName;
            Writer bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath),"utf-8"));
//            BufferedWriter bw=new BufferedWriter(new FileWriter(filepath));
            //替换模板中的占位符并输出
//            List<ArticleEntity> articleEntities= JSON.parseArray(JSON.toJSONString(data),ArticleEntity.class);
            Map<String,Object> map= new HashMap<>();
//            map.put("title","这是一篇文章");
//            map.put("Url","这是一个地址");
            map.put("articleEntitiesList",articleEntitiesList);
            template.process(map,bw, ObjectWrapper.BEANS_WRAPPER);
            bw.close();
            System.out.println("输出路径为："+filepath);
            return filepath;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            long end=System.currentTimeMillis();
            System.out.println("用时："+(end-startTime)/1000+"秒；");
            System.out.println("生成word结束，开始下载。。。");
        }

        return null;
    }

}
