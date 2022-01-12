package ru.lanit.utils.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.Swagger;
import ru.lanit.utils.swagger.interfaces.SwaggerOutputWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME;

public class DefaultSwaggerOutputWriter implements SwaggerOutputWriter {

    private final String outputDirectory;
    private final ObjectMapper mapper;

    public DefaultSwaggerOutputWriter(final String outputDirectory) {
        this.outputDirectory = outputDirectory;
        this.mapper = new ObjectMapper()
                .configure(USE_WRAPPER_NAME_AS_PROPERTY_NAME, true)
                .setSerializationInclusion(NON_NULL);
    }

    @Override
    public void write(Swagger swagger) {
        try (OutputStream outputStream = new FileOutputStream(outputDirectory);
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))
        ) {
            mapper.writeValue(writer, swagger);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
