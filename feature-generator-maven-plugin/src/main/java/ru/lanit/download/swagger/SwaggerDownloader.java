package ru.lanit.download.swagger;

import ru.lanit.download.client.ApiClient;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.requireNonNull;

public class SwaggerDownloader {

    public void getSwaggerSpec(URI uri, String directoryOutput) {
        ApiClient apiClient = new ApiClient();
        String responseBody = apiClient.setURI(uri).get().send();
        writeSwaggerSpec(directoryOutput, responseBody);
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
