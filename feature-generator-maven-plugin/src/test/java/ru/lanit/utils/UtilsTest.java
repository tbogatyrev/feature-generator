package ru.lanit.utils;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.ParseOptions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilsTest {

    @Test
    void getServiceName() {
        String path = "src/test/resources/petstore.json",
                swaggerPath = Utils.getSwaggerPath(URI.create(path));

        OpenAPI openAPI = new OpenAPIParser()
                .readLocation(swaggerPath, null, new ParseOptions()).getOpenAPI();

        String serviceName = Utils.getServiceName(openAPI);

        assertEquals("petstore", serviceName);
    }

    @Test
    void getDirectory() {
        String filePath = "src/main/resources/swagger.json",
                path = Utils.getDirectory(filePath);
        assertEquals("src/main/resources/", path);
    }

    @Test
    void getDownloadedSwagger() {
        String url = "https://petstore.swagger.io/v2/swagger.json",
                path = "src/main/resources/swagger.json";
        Utils.getSwaggerPath(URI.create(url), path);

        assertTrue(new File(path).exists(), "Файл " + path + " отсутствует");

        OpenAPI openAPI = new OpenAPIParser()
                .readLocation(path, null, new ParseOptions()).getOpenAPI();

        assertTrue(openAPI.getPaths().keySet().size() > 0);
    }

    @Test
    void getExistsSwagger() {
        String path = "src/test/resources/petstore.json",
                swaggerPath = Utils.getSwaggerPath(URI.create(path));

        assertEquals(path, swaggerPath, "Файл " + path + " отсутствует");

        OpenAPI openAPI = new OpenAPIParser()
                .readLocation(swaggerPath, null, new ParseOptions()).getOpenAPI();

        assertTrue(openAPI.getPaths().keySet().size() > 0);
    }
}