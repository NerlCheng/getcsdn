package com.getcsdn.Services;

import com.getcsdn.entity.ArticleEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class getCsdnArticle {

    //   获取文章内容
    public ArticleEntity getArticleFromUrl(String detailurl, String title, String blogName) {
        ArticleEntity articleEntity = new ArticleEntity();
        if (detailurl.contains(blogName)) {
            try {

//                睡眠，防止，不停的访问，导致csdn认为是恶意攻击
                Thread.currentThread().sleep(5000);
//                获取到博客的整个内容
                Document document = Jsoup.connect(detailurl).userAgent("Mozilla/5.0").timeout(9000).post();
//                获取博客的标题
//                Element elementTitle = document.getElementsByClass("title-article").first();//标题。 这边根据class的内容来过滤
//                获取博客内容
                Element elementContent = document.getElementsByClass("article_content").first();//内容。
//                  获取博客时间
                String date = document.getElementsByClass("time").first().toString();

                articleEntity.setTitle(title);
                articleEntity.setUrl(detailurl);
                articleEntity.setArticleContent(elementContent);
                articleEntity.setCreateDate(date);

//
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("爬取完成博客：《"+title+"》");
        return articleEntity;
    }
}
