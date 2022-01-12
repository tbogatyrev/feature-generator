package ru.lanit.generator;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ApiTestsGeneratorTest {

    @Test
    void checkSwaggerByFilePath() {
        String path = "src/test/resources/petstore.json";

        new ApiTestsGenerator(URI.create(path))
                .setDirectoryToGeneratePayloadTemplates("src/test/resources/payloads/")
                .generate();


    }


}