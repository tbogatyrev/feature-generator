package ru.lanit.utils.files.checkers;

import com.codeborne.pdftest.PDF;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PdfFileChecker implements DefaultFileChecker {

    @Override
    public void check(InputStream inputStream, List<String> expectedValues) throws IOException {
        PDF pdf = new PDF(inputStream);
        expectedValues.forEach(line -> {
            assertThat(pdf.text).contains(line);
        });
    }
}
