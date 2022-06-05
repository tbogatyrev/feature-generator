package ru.lanit.utils.files.checkers;

import ru.lanit.exceptions.FileCheckerException;

public class FileCheckerFactory {

    public static DefaultFileChecker create(String fileType) {
        switch (fileType.toLowerCase()) {
            case "csv":
                return new CsvFileChecker();
            case "pdf":
                return new PdfFileChecker();
            case "xls":
                return new XlsFileChecker();
            case "rtf":
                return new RtfFileChecker();
            default:
                throw new FileCheckerException(String.format("Неизвестный тип файла %s", fileType));
        }
    }
}
