package ru.lanit.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.internal.JsonContext;

import java.util.Iterator;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class PayloadUtils {

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public static ObjectMapper getJsonMapper() {
        return jsonMapper;
    }

    public static String getParametricRequestBody(String requestBody, Map<String, Object> params) {

        if (requestBody == null) {
            return null;
        }

        String quotationMarkedJson = null;
        try {
            quotationMarkedJson = jsonMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(getParametricContext(requestBody, params).json());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return requireNonNull(quotationMarkedJson)
                .replace("\"__", "")
                .replace("__\"", "");
    }

    public static JsonContext getParametricContext(String requestBody, Map<String, Object> params) {
        String charsMark = "__";
        JsonNode node = getJsonNode(requestBody);

        JsonContext context = (JsonContext) JsonPath.parse(requireNonNull(node).toString());
        params.forEach((key, value) -> {
            String jsonPath = "$." + key;
            boolean isString = (context.read(jsonPath) instanceof String);
            context.set("$." + key,
                    (isString ? "" : charsMark)
                            + "${"
                            + key
                            + "}"
                            + (isString ? "" : charsMark)
            );
        });
        return context;
    }

    public static JsonNode getJsonNode(String requestBody) {
        JsonNode node = null;
        try {
            node = jsonMapper.readTree(requestBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return node;
    }

    public static void generatePayloadsVariables(JsonNode node, String fieldNameNode, Map<String, JsonNode> payloadsVariables) {
        StringBuilder fullNameField = new StringBuilder();
        Iterator<String> fieldsIterator = node.fieldNames();
        Iterator<JsonNode> elementsIterator = node.elements();
        if (!fieldsIterator.hasNext()) {
            if (elementsIterator.hasNext()) {
                payloadsVariables.put("[0]", elementsIterator.next());
            } else if (node.isValueNode()) {
                JsonNode jsonNode = jsonMapper.createArrayNode().add("string");
                payloadsVariables.put("[0]", jsonNode.get(0));
            }
        }

        while (fieldsIterator.hasNext()) {
            String fieldName = fieldsIterator.next();
            if (!node.get(fieldName).isObject() && !node.get(fieldName).isArray()) {
                payloadsVariables.put(fieldNameNode + fieldName, node.get(fieldName));
            } else if (node.get(fieldName).isObject()) {
                fullNameField
                        .append(fieldNameNode)
                        .append(fieldName)
                        .append(".");
                generatePayloadsVariables(node.get(fieldName), fullNameField.toString(), payloadsVariables);
                fullNameField = new StringBuilder();
            } else if (node.get(fieldName).isArray()) {
                for (int i = 0; i < node.get(fieldName).size(); i++) {
                    if (node.get(fieldName).elements().hasNext() && !node.get(fieldName).elements().next().isObject()) {
                        fullNameField
                                .append(fieldNameNode)
                                .append(fieldName)
                                .append("[")
                                .append(i)
                                .append("]");
                        payloadsVariables.put(fullNameField.toString(), node.get(fieldName).get(i));
                    } else {
                        fullNameField
                                .append(fieldNameNode)
                                .append(fieldName)
                                .append("[")
                                .append(i)
                                .append("]")
                                .append(".");
                        generatePayloadsVariables(node.get(fieldName).get(i), fullNameField.toString(), payloadsVariables);
                    }
                    fullNameField = new StringBuilder();
                }
            }
        }
    }
}
