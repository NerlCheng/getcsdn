package com.getcsdn.getcsdn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.constraints.Null;

@Controller
public class mysearchtest {
    private static String blogName = "aiming66";
    private static final String PRE_URL = "https://blog.csdn.net/";
    private static final String SUF_URL = "/article/list/";
    List<String>listArticle = new ArrayList<>();



    @Resource
    private getCsdnTitleAndUrl getCsdnTitleAndUrl;
    @Resource
    private  getCsdnArticle getCsdnArticle;



    @RequestMapping("/getcsdn")
    public @ResponseBody void   main(String[] args) {
        try {
//            获取博客的连接和标题
            List<String[]> list = getCsdnTitleAndUrl.getcsdn(PRE_URL, SUF_URL,blogName);

//            for (int index =0;index < list.size();index ++                 ) {
//                listArticle.add(index,getCsdnArticle(blogName));
//            }
            list.forEach(e ->  getCsdnArticle.getArticleFromUrl(e[0],blogName));



        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }


    /**
     * 保存文章到本地
     *
     * @param titile
     * @param content
     * @param blogName
     */
    public static void saveArticle(String titile, String content, String blogName) {
        String lujing = "d:\\MyLoadArticle\\" + blogName + "\\" + titile + ".txt";//保存到本地的路径和文件名
        File file = new File(lujing);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

