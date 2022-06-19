package ru.lanit.steps;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.ru.И;
import io.restassured.response.Response;
import ru.lanit.steps.types.SmartMapContainer;

import java.util.Map;

import static ru.lanit.utils.storage.Stash.STASH;

public class VariableSteps extends BaseSteps {

    @И("использую тестовые данные")
    public void useTestData(SmartMapContainer values) {
        values.getSmartMap().forEach(STASH::put);
    }

    @И("сохраняю значения из ответа по JPath")
    public void checkHttpCode(Map<String, String> values) {
        Response response = STASH.getAs("response");
        String body = response.body().asString();
        values.forEach((variableName, jsonPath) -> STASH.put(variableName, JsonPath.read(body, jsonPath)));
    }

    @И("сохраняю ответ в переменную {string}")
    public void checkHttpCode(String responseName) {
        Response response = STASH.getAs("response");
        STASH.put(responseName, response.body().asString());
    }
}
