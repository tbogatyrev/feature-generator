package ru.lanit.utils.files.checkers;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RtfFileChecker implements DefaultFileChecker {

    @Override
    public void check(InputStream inputStream, List<String> expectedValues) throws IOException, BadLocationException {
        RTFEditorKit rtfParser = new RTFEditorKit();
        Document document = rtfParser.createDefaultDocument();
        rtfParser.read(inputStream, document, 0);
        expectedValues.forEach(line -> {
            try {
                assertThat(document.getText(0, document.getLength())).contains(line);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
    }
}
