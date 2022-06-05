package ru.lanit.utils.files.checkers;

import com.opencsv.exceptions.CsvException;

import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface DefaultFileChecker {

    void check(InputStream inputStream, List<String> expectedValues) throws IOException, BadLocationException, CsvException;
}
