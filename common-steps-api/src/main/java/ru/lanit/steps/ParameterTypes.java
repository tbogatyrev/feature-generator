package ru.lanit.steps;

import io.cucumber.java.ParameterType;
import ru.lanit.utils.FunctionUtils;

public class ParameterTypes {

    @ParameterType(
            value = ".*",
            name = "smartString"
    )
    public String smartString(String gherkinFunction) {
        return FunctionUtils.replaceFunction(gherkinFunction);
    }
}
