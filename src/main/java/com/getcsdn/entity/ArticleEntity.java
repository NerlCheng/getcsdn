package com.getcsdn.entity;

import org.jsoup.nodes.Element;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class ArticleEntity {
//    标题
    private String title;
//    博客连接
    private String Url;
//    博客内容
    private Element  ArticleContent;
//    博客的发布时间
    private String CreateDate;
}
