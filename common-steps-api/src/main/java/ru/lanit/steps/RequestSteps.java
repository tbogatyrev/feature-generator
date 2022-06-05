package ru.lanit.steps;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.И;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.lanit.config.ProjectConfigHelper;

import java.util.Map;

import static ru.lanit.utils.VariableUtils.replaceMapsVariablesFromStash;
import static ru.lanit.utils.VariableUtils.replaceVariablesFromStash;
import static ru.lanit.utils.files.FileUtils.readFileToString;
import static ru.lanit.utils.storage.Stash.STASH;

public class RequestSteps extends BaseSteps {

    @Если("добавить base URI")
    public void addBaseUriFromProperties() {
        String baseUri = ProjectConfigHelper.getBaseUrl();
        RequestSpecification specification = getRequestSpecification()
                .baseUri(baseUri);
        STASH.put("specification", specification);
        LOGGER.info("Добавлен base uri: {}", baseUri);
    }

    @Если("добавить base URI {string}")
    public void addBaseUri(String baseUri) {
        RequestSpecification specification = getRequestSpecification()
                .baseUri(baseUri);
        STASH.put("specification", specification);
        LOGGER.info("Добавлен base uri: {}", baseUri);
    }

    @Если("добавить заголовки")
    public void addHeaders(Map<String, String> headers) {
        RequestSpecification specification = STASH.getAs("specification");
        specification
                .headers(headers);
        STASH.put("specification", specification);
        LOGGER.info("Добавлены заголовки: {}", headers.toString());
    }

    @И("добавить тело запроса {string}")
    public void addPayload(String payloadTemplatePath) {
        String payload = readFileToString(payloadTemplatePath);
        RequestSpecification specification = STASH.getAs("specification");
//        specification.body(replaceFunctionsInString(replaceVariablesFromStash(payload)));
    }

    @И("отправить запрос {string} по адресу {string} и получен ответ")
    public void sendRequestAndReceiveResponse(String httpMethod, String path) {
        RequestSpecification specification = STASH.getAs("specification");
        specification.basePath(path);
        Response response = specification.request(httpMethod);
        STASH.put("response", response);
    }

    @И("добавить query параметры")
    public void setQueryParams(Map<String, String> queryParams) {
        Map<String, String> replacedQueryParams = replaceMapsVariablesFromStash(queryParams);
        queryParams.forEach((key, value) -> replacedQueryParams.put(key, replaceVariablesFromStash(value)));
        STASH.put("queryParams", replacedQueryParams);
        RequestSpecification specification = STASH.getAs("specification");
        specification.queryParams(replacedQueryParams);
        LOGGER.info("Добавлены query параметры: {}", replacedQueryParams.toString());
    }

    @И("добавить path параметры")
    public void setPathParams(Map<String, String> pathParams) {
        Map<String, String> replacedPathParams = replaceMapsVariablesFromStash(pathParams);
        pathParams.forEach((key, value) -> replacedPathParams.put(key, replaceVariablesFromStash(value)));
        STASH.put("queryParams", replacedPathParams);
        RequestSpecification specification = STASH.getAs("specification");
        specification.pathParams(replacedPathParams);
        LOGGER.info("Добавлены path параметры: {}", replacedPathParams.toString());
    }

    @И("заменяю переменные в ответе {string} и сохраняю в переменную {string}")
    public void changeValuesInResponse(String responseName, String requestName, Map<String, String> values) {
        Response response = STASH.getAs(responseName);
        DocumentContext context = JsonPath.parse(response.body());
        values.forEach(context::set);
        STASH.put(requestName, context.jsonString());
    }

    @И("добавляю тело запроса из переменной {string}")
    public void addPayloadFromVariable(String variableName) {
        String payload = STASH.getAsString(variableName);
        RequestSpecification specification = STASH.getAs("specification");
//        specification.body(replaceFunctionsInString(replaceVariablesFromStash(payload)));
    }
}
