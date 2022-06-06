package ru.lanit.utils;

import ru.lanit.utils.functions.FunctionFactory;
import ru.lanit.utils.functions.models.FunctionModel;
import ru.lanit.utils.functions.parsers.FunctionParser;

public class FunctionUtils {

    public static String replaceFunction(String function) {
        String replacedVariable = VariableUtils.replaceVariablesFromStash(function);
        if (FunctionParser.validate(replacedVariable)) {
            FunctionModel functionModel = FunctionParser.parse(replacedVariable);
            return FunctionFactory.create(functionModel.getFunctionName()).execute(functionModel.getFunctionParams());
        }
        return function;
    }
}
