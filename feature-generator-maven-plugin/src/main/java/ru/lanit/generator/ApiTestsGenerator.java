package ru.lanit.generator;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.ParseOptions;
import org.jetbrains.annotations.NotNull;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.DefaultCodegen;
import org.openapitools.codegen.TemplateManager;
import org.openapitools.codegen.api.TemplatePathLocator;
import org.openapitools.codegen.templating.MustacheEngineAdapter;
import org.openapitools.codegen.templating.TemplateManagerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lanit.ResourceTemplateLoader;
import ru.lanit.models.FeatureModel;
import ru.lanit.utils.swagger.map.TreeMapForGherkinTable;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.lanit.generator.enums.Constants.PATH_DELIMITER;
import static ru.lanit.generator.enums.Constants.UNDERSCORE;
import static ru.lanit.generator.enums.DirectoryEnum.*;
import static ru.lanit.generator.enums.RegexEnum.CYRILLIC;
import static ru.lanit.generator.enums.RegexEnum.PART_OF_PATH;
import static ru.lanit.utils.PayloadUtils.*;
import static ru.lanit.utils.Utils.*;

public class ApiTestsGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiTestsGenerator.class);

    private final Map<String, JsonNode>
            bodyRequestVariables = new TreeMapForGherkinTable<>(),
            bodyResponseVariables = new TreeMapForGherkinTable<>();

    private static OpenAPI openAPI;
    private DefaultCodegen codegen;

    private String
            pathToDownloadSwagger,
            directoryToGeneratePayloadTemplates,
            directoryToGenerateFeatureFiles,
            groupId,
            artifactId,
            projectVersion;

    private boolean generateAdditionalFiles = false;
    private final URI uri;

    private List<FeatureModel> featureModels;

    public ApiTestsGenerator(URI uri) {
        this.uri = uri;
    }

    public ApiTestsGenerator setPathToDownloadSwagger(String pathToDownloadSwagger) {
        this.pathToDownloadSwagger = pathToDownloadSwagger;
        return this;
    }

    public ApiTestsGenerator setDirectoryToGeneratePayloadTemplates(String directoryToGeneratePayloadTemplates) {
        this.directoryToGeneratePayloadTemplates = directoryToGeneratePayloadTemplates;
        return this;
    }

    public ApiTestsGenerator generateAdditionalFiles() {
        this.generateAdditionalFiles = true;
        return this;
    }

    public ApiTestsGenerator setDirectoryToGenerateFeatureFiles(String directoryToGenerateFeatureFiles) {
        this.directoryToGenerateFeatureFiles = directoryToGenerateFeatureFiles;
        return this;
    }

    public ApiTestsGenerator setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public ApiTestsGenerator setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public ApiTestsGenerator setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
        return this;
    }

    public void generate() {

        if (pathToDownloadSwagger == null) {
            pathToDownloadSwagger = SWAGGER_OUTPUT.getPath();
        }
        createDirectoriesIfNotExist(getDirectory(pathToDownloadSwagger));

        String swaggerPathLocation = getSwaggerLocation(uri, pathToDownloadSwagger);

        openAPI = new OpenAPIParser()
                .readLocation(swaggerPathLocation, null, new ParseOptions()).getOpenAPI();

        String serviceName = getServiceName(openAPI);

        String serviceNamePackage = serviceName + PATH_DELIMITER.getConstant();

        codegen = new DefaultCodegen();
        codegen.setOpenAPI(openAPI);

        if (directoryToGeneratePayloadTemplates == null) {
            directoryToGeneratePayloadTemplates = DIRECTORY_TO_GENERATE_PAYLOADS_TEMPLATES.getPath() + serviceNamePackage;
        }
        createDirectoriesIfNotExist(directoryToGeneratePayloadTemplates);

        if (directoryToGenerateFeatureFiles == null) {
            directoryToGenerateFeatureFiles = DIRECTORY_TO_GENERATE_FEATURES.getPath() + serviceNamePackage;
        }
        createDirectoriesIfNotExist(directoryToGenerateFeatureFiles);

        List<Map<String, Object>> dataSource = getDataSourceToGenerate();

        writeRequestBodiesToFile(directoryToGeneratePayloadTemplates);
        writeFeatureFiles(dataSource, directoryToGenerateFeatureFiles);
        if (Boolean.TRUE.equals(generateAdditionalFiles)) {
            writeAdditionalFiles(dataSource);
        }
    }

    private void writeAdditionalFiles(List<Map<String, Object>> dataSource) {
        TemplateManager manager = getTemplateManager();
        File
                outputTestRunner = new File(DIRECTORY_TO_GENERATE_TEST_RUNNER.getPath(), "TestRunner.java"),
                outputDefaultProperties = new File(DIRECTORY_TO_GENERATE_RESOURCE.getPath(), "default.properties"),
                outputCredentials = new File(DIRECTORY_TO_GENERATE_RESOURCE.getPath(), "credentials.yml"),
                outputAllureProperties = new File(DIRECTORY_TO_GENERATE_RESOURCE.getPath(), "allure.properties"),
                outputHooks = new File(DIRECTORY_TO_GENERATE_HOOKS.getPath(), "Hooks.java"),
                outputPom = new File("pom.xml");

        Map<String, Object> map = dataSource.get(0);
        try {
            manager.write(map, "testRunner.mustache", outputTestRunner);
            manager.write(map, "defaultProperties.mustache", outputDefaultProperties);
            manager.write(map, "credentials.mustache", outputCredentials);

            createDirectoriesIfNotExist(DIRECTORY_TO_GENERATE_HOOKS.getPath());
            manager.write(map, "hooks.mustache", outputHooks);
            manager.write(map, "pom.mustache", outputPom);
            manager.write(map, "allureProperties.mustache", outputAllureProperties);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void writeRequestBodiesToFile(String directory) {
        List<Map<String, Object>> dataSourceToGenerateFeatures = getDataSourceToGenerate();

        dataSourceToGenerateFeatures.forEach(map -> {
            if (map.get("requestBodyExample") == null) {
                return;
            }
            String fullDirectory = directory + map.get("tag").toString()
                    .replaceAll(CYRILLIC.getRegex(), UNDERSCORE.getConstant())
                    .replaceAll(PART_OF_PATH.getRegex(), UNDERSCORE.getConstant()) + PATH_DELIMITER.getConstant();
            String filePath = fullDirectory + map.get("testCaseName");
            createDirectoriesIfNotExist(fullDirectory);

            try (PrintWriter writer = new PrintWriter(filePath)) {
                writer.write(getParametricRequestBody(map.get("requestBodyExample").toString(), (Map<String, Object>) map.get("requestBodyVariables")));
            } catch (IOException e) {
                LOGGER.info("Не удалось записать файл " + filePath + " " + e.getMessage());
            }
            LOGGER.info("Создан файл: " + filePath);
        });
    }

    private void writeFeatureFiles(List<Map<String, Object>> data, String outputDirectory) {
        TemplateManager manager = getTemplateManager();
        for (Map<String, Object> map : data) {
            String fullOutputDirectory = outputDirectory + PATH_DELIMITER.getConstant() + map.get("tag").toString()
                    .replaceAll(CYRILLIC.getRegex(), UNDERSCORE.getConstant())
                    .replaceAll(PART_OF_PATH.getRegex(), UNDERSCORE.getConstant()) + PATH_DELIMITER.getConstant();
            File output = new File(fullOutputDirectory, map.get("testCaseName") + ".feature");
            try {
                manager.write(map, "feature.mustache", output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private TemplateManager getTemplateManager() {
        return new TemplateManager(
                new TemplateManagerOptions(false, false),
                new MustacheEngineAdapter(),
                new TemplatePathLocator[]{new ResourceTemplateLoader()}
        );
    }

    private List<FeatureModel> getFeatureModels() {
        return featureModels == null ? createFeatureModels() : featureModels;
    }

    private List<FeatureModel> createFeatureModels() {
        featureModels = new ArrayList<>();

        openAPI.getPaths().forEach((path, pathItem) -> pathItem.readOperationsMap().forEach((method, operation) -> {
            String httpMethod = method.name();
            CodegenOperation codegenOperation = codegen.fromOperation(path, String.valueOf(method), operation, null);
            operation.getResponses().forEach((statusCode, response) -> {
                FeatureModel featureModel = new FeatureModel();

                List<Map<String, String>>
                        requestBodyExamples = codegenOperation.requestBodyExamples,
                        consumes = codegenOperation.consumes,
                        produces = codegenOperation.produces,
                        responseBodyExamples = codegenOperation.examples;
                String baseUrl = openAPI.getServers().get(0).getUrl();

                featureModel.setPath(path)
                        .setSummary(operation.getSummary())
                        .setHttpMethod(httpMethod)
                        .setStatusCode(statusCode)
                        .setTag(operation.getTags().get(0))
                        .setCodegenOperation(codegenOperation)
                        .setRequestBodyExample(requestBodyExamples != null ? requestBodyExamples.get(0).get("example") : null)
                        .setName(createName(path, httpMethod, statusCode))
                        .setConsumes(consumes != null ? consumes.get(0).get("mediaType") : null)
                        .setProduce(produces != null ? produces.get(0).get("mediaType") : null)
                        .setResponseBodyExample(responseBodyExamples != null ? responseBodyExamples.get(0).get("example") : null)
                        .setBaseUrl(baseUrl);
                featureModels.add(featureModel);
            });
        }));
        return featureModels;
    }

    @NotNull
    private String createName(String path, String httpMethod, String statusCode) {
        return path
                .replaceFirst(PATH_DELIMITER.getConstant(), "")
                .replaceAll(PATH_DELIMITER.getConstant(), UNDERSCORE.getConstant()) + UNDERSCORE.getConstant() + httpMethod + UNDERSCORE.getConstant() + statusCode;
    }

    private void generateBodyRequestVariables(JsonNode node) {
        generatePayloadsVariables(node, "", bodyRequestVariables);
    }

    private void generateBodyResponseVariables(JsonNode node) {
        generatePayloadsVariables(node, "", bodyResponseVariables);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getDataSourceToGenerate() {
        List<Map<String, Object>> operationsData = new ArrayList<>();

        getFeatureModels().forEach(featureModel -> {
            Map<String, Object> data = new HashMap<String, Object>(getJsonMapper().convertValue(featureModel.getCodegenOperation(), Map.class));
            data.put("testCaseName", featureModel.getName());
            data.put("tag", featureModel.getTag());
            data.put("requestBodyExample", featureModel.getRequestBodyExample());
            data.put("baseUrl", featureModel.getBaseUrl());
            data.put("hasHeaders", featureModel.getConsumes() != null || featureModel.getProduce() != null);

            if (Boolean.TRUE.equals(generateAdditionalFiles)) {
                data.put("groupId", groupId);
                data.put("artifactId", artifactId);
                data.put("projectVersion", projectVersion);
            }
            String payloadsFullPath = directoryToGeneratePayloadTemplates + featureModel.getTag()
                    .replaceAll(CYRILLIC.getRegex(), UNDERSCORE.getConstant())
                    .replaceAll(PART_OF_PATH.getRegex(), UNDERSCORE.getConstant()) + PATH_DELIMITER.getConstant() + featureModel.getName();
            data.put("payloadPath", payloadsFullPath);
            data.put("shortPayloadPath", payloadsFullPath.replaceAll(".*resources/", ""));
            data.put("pathParamsMap", getEndpointsParamsMap(data, "pathParams"));
            data.put("queryParamsMap", getEndpointsParamsMap(data, "queryParams"));
            data.put("response", featureModel.getStatusCode());

            if (featureModel.getRequestBodyExample() != null) {
                JsonNode node = getJsonNode(featureModel.getRequestBodyExample());
                generateBodyRequestVariables(node);
                data.put("hasRequestBody", true);
                Map<String, Object> requestExamplesMap = new TreeMapForGherkinTable<>();
                bodyRequestVariables.forEach(requestExamplesMap::put);
                data.put("requestBodyVariables", requestExamplesMap);
            } else {
                data.put("hasRequestBody", false);
            }

            if (featureModel.getResponseBodyExample() != null) {
                JsonNode node = getJsonNode(featureModel.getResponseBodyExample());
                generateBodyResponseVariables(node);
                data.put("hasResponseBody", true);
                Map<String, Object> responseExamplesMap = new TreeMapForGherkinTable<>();
                bodyResponseVariables.forEach((key, value) -> responseExamplesMap.put("$." + key, value));
                data.put("responseBodyVariables", responseExamplesMap);
            } else {
                data.put("hasResponseBody", false);
            }

            if (featureModel.getResponseMessage() != null) {
                data.put("hasResponseDescription", true);
                data.put("responseDescription", featureModel.getResponseMessage());
            } else {
                data.put("hasResponseDescription", false);
            }

            operationsData.add(data);
            bodyRequestVariables.clear();
            bodyResponseVariables.clear();
        });
        return operationsData;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getEndpointsParamsMap(Map<String, Object> data, String paramsName) {
        Map<String, Object> pathParamsMap = new TreeMapForGherkinTable<>();
        List<Object> pathParamsList = (ArrayList<Object>) data.get(paramsName);
        pathParamsList.forEach(params -> pathParamsMap.put((String) ((Map<String, Object>) params).get("paramName"),
                ((Map<String, Object>) params).get("example") != null
                        ? ((Map<String, Object>) params).get("example")
                        : "no example"
        ));
        return pathParamsMap;
    }
}