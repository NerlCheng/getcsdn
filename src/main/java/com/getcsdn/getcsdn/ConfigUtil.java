package com.getcsdn.getcsdn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class ConfigUtil
{
    public static Properties pro;
    
    static {
        ConfigUtil.pro = new Properties();
        final Resource resource = (Resource)new ClassPathResource("/config.properties", (Class)ConfigUtil.class);
        try (final InputStream inputStream = resource.getInputStream()) {
            if (null != inputStream) {
                ConfigUtil.pro.load(inputStream);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
