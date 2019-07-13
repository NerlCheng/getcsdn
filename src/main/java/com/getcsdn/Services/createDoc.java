package com.getcsdn.Services;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Map;

public interface createDoc {
    public String createDoc(String path, String templatePath, Map<String, Object> data, String templateName, String docName) throws IOException;
}
