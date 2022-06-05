package ru.lanit.generator.enums;

public enum RegexEnum {
    SERVICE_NAME_REGEX("\\..+"),
    DIRECTORY_REGEX("([^\\/]+\\..+)"),
    CYRILLIC ("[^\\w^А-Яа-я]"),
    PART_OF_PATH("_{2,}");

    private final String regex;

    RegexEnum(String s) {
        this.regex = s;
    }

    public String getRegex() {
        return regex;
    }
}
