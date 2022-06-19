package ru.lanit.steps.types;

import io.cucumber.java.ParameterType;

import static ru.lanit.utils.FunctionUtils.replaceFunction;

public class ParameterTypes {

    @ParameterType(
            value = "\"(.*)\"",
            name = "smartString"
    )
    public String smartString(String gherkinFunction) {
        return replaceFunction(gherkinFunction);
    }
}
