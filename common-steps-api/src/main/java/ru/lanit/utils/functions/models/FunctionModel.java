package ru.lanit.utils.functions.models;

import java.util.List;

public class FunctionModel {

    private final String name;
    private final List<String> params;

    public FunctionModel(String functionName, List<String> functionParams) {
        this.name = functionName;
        this.params = functionParams;
    }

    public String getFunctionName() {
        return name;
    }

    public List<String> getFunctionParams() {
        return params;
    }
}
