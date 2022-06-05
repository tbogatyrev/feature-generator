package ru.lanit.download.swagger;

import ru.lanit.download.client.ApiClient;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.requireNonNull;
import static ru.lanit.generator.enums.RegexEnum.SERVICE_NAME_REGEX;

public class SwaggerDownloader {

    private String swaggerFileLocation;

    public void download(URI uri, String directoryOutput) {
        ApiClient apiClient = new ApiClient();
        String responseBody = apiClient.setURI(uri).get().send();
        swaggerFileLocation = directoryOutput + uri.getHost().replaceAll(SERVICE_NAME_REGEX.getRegex(), "") + ".json";
        writeSwaggerSpec(swaggerFileLocation, responseBody);
    }

    public String getSwaggerFileLocation() {
        return swaggerFileLocation;
    }

    private void writeSwaggerSpec(String directoryOutput, String swaggerSpec) {
        try (OutputStream outputStream = new FileOutputStream(directoryOutput);
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))
        ) {
            requireNonNull(writer).write(requireNonNull(swaggerSpec));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
