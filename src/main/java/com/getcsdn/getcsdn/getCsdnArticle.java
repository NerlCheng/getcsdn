package com.getcsdn.getcsdn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class getCsdnArticle {

    //   获取文章内容
    public void getArticleFromUrl(String detailurl, String blogName, List<Element> elementList) {
        if (detailurl.contains(blogName)) {
            try {
                Thread.currentThread().sleep(2000);
                Document document = Jsoup.connect(detailurl).userAgent("Mozilla/5.0").timeout(3000).post();
                Element elementTitle = document.getElementsByClass("title-article").first();//标题。 这边根据class的内容来过滤
//                System.out.println(elementTitle.text());
                String filename = elementTitle.text().replaceAll("/", "或");
                Element elementContent = document.getElementsByClass("article_content").first();//内容。
                elementList.add(elementContent);
//                System.out.println(elementContent);
//            saveArticle(filename, elementContent.text(), blogName);
                // String Content =elementContent.te  xt().replaceAll(" ", "\t");
                // System.out.println(elementContent.text()+"\n");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
