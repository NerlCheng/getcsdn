package com.getcsdn.Services;

import com.getcsdn.entity.ArticleEntity;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface createDoc {
    public String createDoc(String path, String templatePath,List<ArticleEntity> articleEntitiesList, String templateName, String docName) throws IOException;
}
