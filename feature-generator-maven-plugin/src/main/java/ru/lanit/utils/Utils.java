package ru.lanit.utils;

import io.swagger.v3.oas.models.OpenAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lanit.download.swagger.SwaggerDownloader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ru.lanit.generator.enums.RegexEnum.DIRECTORY_REGEX;
import static ru.lanit.generator.enums.RegexEnum.SERVICE_NAME_REGEX;

public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public static String getServiceName(OpenAPI openAPI) {
        String name = null;
        try {
            name = URI.create(openAPI.getServers().get(0).getUrl())
                    .toURL()
                    .getHost()
                    .replaceAll(SERVICE_NAME_REGEX.getRegex(), "");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String getDirectory(String filePath) {
        return filePath.replaceAll(DIRECTORY_REGEX.getRegex(), "");
    }

    public static String getSwaggerPath(URI uri, String pathToDownload) {
        if (uri.getScheme() != null && uri.getScheme().contains("http")) {
            new SwaggerDownloader().getSwaggerSpec(uri, pathToDownload);

            return pathToDownload;
        } else {

            return uri.getRawPath();
        }
    }

    public static String getSwaggerPath(URI uri) {
        return getSwaggerPath(uri, "");
    }

    public static void createDirectoriesIfNotExist(String directory) {
        if (Files.notExists(Paths.get(directory))) {
            try {
                Files.createDirectories(Paths.get(directory));
                LOGGER.info("Создана директория: " + directory);
            } catch (IOException e) {
                LOGGER.error("Путь " + directory + " не найден. " + e.getMessage());
            }
        } else {
            LOGGER.info("Используется существующая директория: " + directory);
        }
    }
}
