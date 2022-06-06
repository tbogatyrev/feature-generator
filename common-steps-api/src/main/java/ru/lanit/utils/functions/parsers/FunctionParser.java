package ru.lanit.utils.functions.parsers;

import ru.lanit.utils.functions.models.FunctionModel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public class FunctionParser {

    private static final String REGEX = "(^СТРОКА|ЧИСЛО|ДАТА.*\\)$)";
    private static final String REGEX_FUNCTION_NAME = "(СТРОКА|ЧИСЛО|ДАТА)";

    public static FunctionModel parse(String gherkinFunction) {
        String functionName = null;
        Pattern pattern = Pattern.compile(REGEX_FUNCTION_NAME);
        Matcher matcher = pattern.matcher(gherkinFunction);
        if (matcher.find()) {
            functionName = matcher.group();
        }
        List<String> functionParameters = List.of(
                gherkinFunction
                        .replaceAll(requireNonNull(functionName), "")
                        .replaceAll("[\\(\\)]", "")
                        .split(","));
        return new FunctionModel(functionName, functionParameters);
    }

    public static boolean validate(String gherkinFunction) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(gherkinFunction);
        return matcher.find();
    }
}
