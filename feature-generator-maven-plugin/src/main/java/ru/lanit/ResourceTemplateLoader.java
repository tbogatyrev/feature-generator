package ru.lanit;

import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.TemplateManager;
import org.openapitools.codegen.api.TemplatePathLocator;

import java.io.File;
import java.net.URL;

public class ResourceTemplateLoader implements TemplatePathLocator {

    @Override
    public String getFullTemplatePath(String relativeTemplateFile) {
        if (StringUtils.isNotEmpty(relativeTemplateFile)) {
            String resourceLocation = "templates";
            String loc = resourceLocation + File.separator + relativeTemplateFile;

            URL url = this.getClass().getClassLoader().getResource(TemplateManager.getCPResourcePath(loc));
            if (url != null) {
                return loc;
            }
        }
        return null;
    }
}
