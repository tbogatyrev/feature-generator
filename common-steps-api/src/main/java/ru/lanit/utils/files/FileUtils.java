package ru.lanit.utils.files;


import org.apache.commons.io.IOUtils;
import ru.lanit.exceptions.FileUtilsException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static String readFileToString(String fileName) {
        String fileAsString;
        InputStream inputStream;
        try {
            inputStream = FileUtils.class.getClassLoader().getResourceAsStream(fileName);
            fileAsString = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileUtilsException("Can't parse file: " + fileName);
        }
        return fileAsString;
    }
}
