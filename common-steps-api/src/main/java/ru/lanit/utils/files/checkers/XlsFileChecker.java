package ru.lanit.utils.files.checkers;

import com.codeborne.xlstest.XLS;

import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class XlsFileChecker implements DefaultFileChecker {

    @Override
    public void check(InputStream inputStream, List<String> expectedValues) throws IOException, BadLocationException {
        XLS xls = new XLS(inputStream);
        List<String> cellsValue = new ArrayList<>();
        xls.excel.getSheetAt(0)
                .forEach(line -> line.cellIterator().forEachRemaining(cell -> cellsValue.add(cell.getStringCellValue())));
        expectedValues.forEach(line -> {
            assertThat(cellsValue.toString()).contains(line);
        });
    }
}
