package ru.lanit.models;

import org.openapitools.codegen.CodegenOperation;

import java.util.Map;

public class FeatureModel {

    private String path,
            name,
            httpMethod,
            statusCode,
            summary,
            tag,
            requestBodyExample,
            responseBodyExample,
            consumes,
            produce,
            responseMessage,
            baseUrl;

    private CodegenOperation codegenOperation;

    Map<String, String> requestBodyParams;

    public String getPath() {
        return path;
    }

    public FeatureModel setPath(String path) {
        this.path = path;
        return this;
    }

    public String getName() {
        return name;
    }

    public FeatureModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public FeatureModel setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public FeatureModel setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public FeatureModel setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public FeatureModel setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public CodegenOperation getCodegenOperation() {
        return codegenOperation;
    }

    public FeatureModel setCodegenOperation(CodegenOperation codegenOperation) {
        this.codegenOperation = codegenOperation;
        return this;
    }

    public String getRequestBodyExample() {
        return requestBodyExample;
    }

    public FeatureModel setRequestBodyExample(String requestBodyExample) {
        this.requestBodyExample = requestBodyExample;
        return this;
    }

    public String getConsumes() {
        return consumes;
    }

    public FeatureModel setConsumes(String consumes) {
        this.consumes = consumes;
        return this;
    }

    public String getProduce() {
        return produce;
    }

    public FeatureModel setProduce(String produce) {
        this.produce = produce;
        return this;
    }

    public String getResponseBodyExample() {
        return responseBodyExample;
    }

    public FeatureModel setResponseBodyExample(String responseBodyExample) {
        this.responseBodyExample = responseBodyExample;
        return this;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public FeatureModel setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
        return this;
    }

    public Map<String, String> getRequestBodyParams() {
        return requestBodyParams;
    }

    public FeatureModel setRequestBodyParams(Map<String, String> requestBodyParams) {
        this.requestBodyParams = requestBodyParams;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public FeatureModel setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
}
