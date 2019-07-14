package com.getcsdn.entity;

import org.jsoup.nodes.Element;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class ArticleEntity {
//    标题
//@Column(name="title")
private String title;
//    博客连接
//@Column(name="Url")
private String Url;
//    博客内容
//@Column(name="ArticleContent")
    private Element  ArticleContent;
//    博客的发布时间
//@Column(name="CreateDate")
    private String CreateDate;
}
