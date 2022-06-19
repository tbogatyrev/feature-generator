package ru.lanit.steps;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.И;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.lanit.config.ProjectConfigHelper;
import ru.lanit.steps.types.SmartMapContainer;

import static ru.lanit.utils.VariableUtils.replaceVariablesFromStash;
import static ru.lanit.utils.files.FileUtils.readFileToString;
import static ru.lanit.utils.storage.Stash.STASH;

public class RequestSteps extends BaseSteps {

    @Если("добавить base URI")
    public void addBaseUriFromProperties() {
        String baseUri = ProjectConfigHelper.getBaseUrl();
        RequestSpecification specification = getRequestSpecification().baseUri(baseUri);
        STASH.put("specification", specification);
        LOGGER.info("Добавлен base uri: {}", baseUri);
    }

    @Если("добавить base URI {smartString}")
    public void addBaseUri(String baseUri) {
        RequestSpecification specification = getRequestSpecification().baseUri(baseUri);
        STASH.put("specification", specification);
        LOGGER.info("Добавлен base uri: {}", baseUri);
    }

    @Если("добавить заголовки")
    public void addHeaders(SmartMapContainer mapContainer) {
        RequestSpecification specification = STASH.getAs("specification");
        specification.headers(mapContainer.getSmartMap());
        STASH.put("specification", specification);
        LOGGER.info("Добавлены заголовки: {}", mapContainer.getSmartMap().toString());
    }

    @И("добавить тело запроса {smartString}")
    public void addPayload(String payloadTemplatePath) {
        String payload = readFileToString(payloadTemplatePath);
        RequestSpecification specification = STASH.getAs("specification");
        specification.body(replaceVariablesFromStash(payload));
    }

    @И("отправить запрос {smartString} по адресу {smartString} и получен ответ")
    public void sendRequestAndReceiveResponse(String httpMethod, String path) {
        RequestSpecification specification = STASH.getAs("specification");
        specification.basePath(path);
        Response response = specification.request(httpMethod);
        STASH.put("response", response);
    }

    @И("добавить query параметры")
    public void setQueryParams(SmartMapContainer queryParams) {
        STASH.put("queryParams", queryParams.getSmartMap());
        RequestSpecification specification = STASH.getAs("specification");
        specification.queryParams(queryParams.getSmartMap());
        LOGGER.info("Добавлены query параметры: {}", queryParams.getSmartMap().toString());
    }

    @И("добавить path параметры")
    public void setPathParams(SmartMapContainer pathParams) {
        STASH.put("pathParams", pathParams.getSmartMap());
        RequestSpecification specification = STASH.getAs("specification");
        specification.pathParams(pathParams.getSmartMap());
        LOGGER.info("Добавлены path параметры: {}", pathParams.getSmartMap().toString());
    }

    @И("заменяю переменные в ответе {smartString} и сохраняю в переменную {smartString}")
    public void changeValuesInResponse(String responseName, String requestName, SmartMapContainer values) {
        Response response = STASH.getAs(responseName);
        DocumentContext context = JsonPath.parse(response.body());
        values.getSmartMap().forEach(context::set);
        STASH.put(requestName, context.jsonString());
    }

    @И("добавляю тело запроса из переменной {smartString}")
    public void addPayloadFromVariable(String variableName) {
        String payload = STASH.getAsString(variableName);
        RequestSpecification specification = STASH.getAs("specification");
        specification.body(replaceVariablesFromStash(payload));
    }
}
