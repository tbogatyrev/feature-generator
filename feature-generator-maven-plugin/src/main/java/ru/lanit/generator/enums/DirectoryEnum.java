package ru.lanit.generator.enums;

public enum DirectoryEnum {
    SWAGGER_OUTPUT("target/swagger-spec/"),
    DIRECTORY_TO_GENERATE_PAYLOADS_TEMPLATES("src/test/resources/templates/payloads/"),
    DIRECTORY_TO_GENERATE_FEATURES("src/test/resources/features/"),
    DIRECTORY_TO_GENERATE_TEST_RUNNER("src/test/java/"),
    DIRECTORY_TO_GENERATE_RESOURCE("src/test/resources/"),
    DIRECTORY_TO_GENERATE_HOOKS("src/test/java/hooks/");

    String path;

    DirectoryEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
