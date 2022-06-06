package ru.lanit.utils.functions.models;

import java.time.temporal.ChronoUnit;

public class DateParamsModel {

    private final String currentDate;
    private String operator;
    private String offset;
    private ChronoUnit offsetDescription;
    private String format;
    private boolean hasFormat;
    private boolean hasOffset;

    public DateParamsModel(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public String getOperator() {
        return operator;
    }

    public DateParamsModel setOperator(String operator) {
        this.operator = operator;
        return this;
    }

    public String getOffset() {
        return offset;
    }

    public DateParamsModel setOffset(String offset) {
        this.offset = offset;
        return this;
    }

    public ChronoUnit getOffsetDescription() {
        return offsetDescription;
    }

    public DateParamsModel setOffsetDescription(ChronoUnit offsetDescription) {
        this.offsetDescription = offsetDescription;
        return this;
    }

    public String getFormat() {
        return format;
    }

    public DateParamsModel setFormat(String format) {
        this.format = format;
        return this;
    }

    public boolean hasFormat() {
        return hasFormat;
    }

    public DateParamsModel setHasFormat(boolean hasFormat) {
        this.hasFormat = hasFormat;
        return this;
    }

    public boolean hasOffset() {
        return hasOffset;
    }

    public DateParamsModel setHasOffset(boolean hasOffset) {
        this.hasOffset = hasOffset;
        return this;
    }
}
