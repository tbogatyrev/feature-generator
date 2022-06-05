package ru.lanit.utils.functions;

import java.util.List;
import java.util.Random;

public class NumberFunction implements Function {

    private static NumberFunction numberFunction;

    private NumberFunction() {
    }

    public static NumberFunction getInstance() {
        if (numberFunction == null) {
            numberFunction = new NumberFunction();
        }
        return numberFunction;
    }

    @Override
    public String execute(List<String> parameterList) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Integer.parseInt(parameterList.get(0)); i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
}
