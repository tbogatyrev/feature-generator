package ru.lanit.steps;

import com.jayway.jsonpath.JsonPath;
import com.opencsv.exceptions.CsvException;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.То;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lanit.utils.files.checkers.DefaultFileChecker;
import ru.lanit.utils.files.checkers.FileCheckerFactory;

import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.lanit.utils.VariableUtils.replaceVariablesFromStash;
import static ru.lanit.utils.storage.Stash.STASH;

public class ValidationSteps extends BaseSteps {

    private static final Logger LOGGER = LogManager.getLogger(ValidationSteps.class);

    @То("статус код {string}")
    public void checkHttpCode(String httpCode) {
        Response response = STASH.getAs("response");
        String actualHttpCode = String.valueOf(response.statusCode());
        assertThat(actualHttpCode).isEqualTo(httpCode);
        LOGGER.info("Ответ пришел с корректным httpCode: {}, ожидаемый {}", actualHttpCode, httpCode);
    }

    @И("валидация ответа по JsonPath прошла успешно")
    public void validateResponse(Map<String, String> map) {
        Response response = STASH.getAs("response");
        String body = response.body().asString();
        map.forEach((jsonPath, expectedValue) -> {
            String actualValue = JsonPath.read(body, jsonPath).toString();
            String replacedExpectedValue = replaceVariablesFromStash(expectedValue);
            assertThat(actualValue).isEqualTo(replacedExpectedValue).as("JsonPath: " + jsonPath);
            LOGGER.info("Фактическое значение: {} соответствует ожидаемому {} по JsonPath: {}", actualValue, replacedExpectedValue, jsonPath);
        });
    }

    @И("ответ содержит {string}")
    public void responseBodyContainsDescription(String description) {
        Response response = STASH.getAs("response");
        LOGGER.info("Получено тело ответа {}", response.body().asString());
        assertThat(response.body().asString()).contains(description);
        LOGGER.info("Ответ содержит {}", description);
    }

    @И("файл {string} содержит")
    public void checkFile(String fileType, List<String> data) throws IOException, CsvException, BadLocationException {
        Response response = STASH.getAs("response");
        InputStream fileStream = response.asInputStream();
        DefaultFileChecker fileChecker = FileCheckerFactory.create(fileType);
        fileChecker.check(fileStream, data);
    }
}
