package ru.lanit.utils.functions;

import io.cucumber.core.exception.CucumberException;

public class FunctionFactory {

    private FunctionFactory() {
    }

    public static Function create(String functionName) {
        switch (functionName) {
            case "СТРОКА":
                return StringFunction.getInstance();
            case "ЧИСЛО":
                return NumberFunction.getInstance();
            case "ДАТА":
                return DateFunction.getInstance();
            default:
                throw new CucumberException("Не верно указано название функции " + functionName);
        }
    }
}
