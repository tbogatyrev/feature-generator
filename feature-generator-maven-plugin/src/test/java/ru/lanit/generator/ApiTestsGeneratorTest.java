package ru.lanit.generator;

import org.junit.jupiter.api.Test;

import java.net.URI;

class ApiTestsGeneratorTest {

    @Test
    void checkSwaggerByFilePath() {
        String path = "src/test/resources/petstore.json";
        String url = "https://petstore.swagger.io/v2/swagger.json";

        new ApiTestsGenerator(URI.create(path))
                .setDirectoryToGeneratePayloadTemplates("src/test/resources/payloads/")
                .generate();
    }
}