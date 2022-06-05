package ru.lanit.steps;

import io.cucumber.java.ParameterType;
import ru.lanit.utils.functions.FunctionFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public class ParameterTypes {

    @ParameterType(
            value = ".*",
            name = "function"
    )
    public String function(String gherkinFunction) {
        String functionName = null;
        Pattern pattern = Pattern.compile("^[А-Яа-я]+");
        Matcher matcher = pattern.matcher(gherkinFunction);
        if (matcher.find()) {
            functionName = matcher.group();
        }
        List<String> functionParameters = List.of(
                gherkinFunction
                        .replaceAll(requireNonNull(functionName), "")
                        .replaceAll("[\\(\\)]", "")
                        .split(","));
        return FunctionFactory.create(functionName).execute(functionParameters);
    }
}
