package com.getcsdn.Services;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class getCsdnTitleAndUrl {
    //获取博客地址和博客标题
    public  List<String[]>  getcsdn(String PRE_URL,String SUF_URL ,String blogName) throws IOException, InterruptedException {
        List<String[]> allData = new ArrayList<>();
        for (int i = 1; i<=2; i++) {
            Thread.currentThread().sleep(800);
            System.out.println(blogName+"您好："+"我正在爬取第" +i + "页的博客连接，请稍等！！！");
            String url = PRE_URL + blogName + SUF_URL + i;
            Scanner scanner = new Scanner(new URL(url).openStream());
            StringBuffer sb = new StringBuffer();
            while (scanner.hasNextLine())
                sb.append(scanner.nextLine());
            List<String[]> list = new ArrayList<>();
            String text = sb.toString();
            String regex = "<div class=\"article-item-box(.*?)<p class=\"content\">";
            String regexInner = "<a href=\"([^\"]+)\".*</span>(.*)</a>";
            Matcher m = Pattern.compile(regex).matcher(text);
            while (m.find()) {
                Matcher m2 = Pattern.compile(regexInner).matcher(m.group(1));
                if (m2.find())
                    list.add(new String[]{m2.group(1), m2.group(2).trim()});
            }
            if (0 == list.size())
                break;
            allData.addAll(list);
            scanner.close();
        }
//        获取了博客的连接和标题
        System.out.println(blogName+"您好："+"我一共获取到："+allData.size()+ "篇博客");
        return allData;

    }
}
