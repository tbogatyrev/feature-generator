package ru.lanit.utils.files.checkers;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvFileChecker implements DefaultFileChecker {

    @Override
    public void check(InputStream inputStream, List<String> expectedValues) throws IOException, BadLocationException, CsvException {
        CSVReader csv = new CSVReader(new InputStreamReader(inputStream));
        List<String[]> csvList = csv.readAll();
        expectedValues.forEach(line -> {
            assertThat(csvList).contains(
                    line.split(","));
        });
    }
}
