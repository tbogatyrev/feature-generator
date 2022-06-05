package ru.lanit.generator.enums;

public enum Constants {
    PATH_DELIMITER("/"),
    UNDERSCORE("_");

    private final String constant;

    Constants(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }
}
