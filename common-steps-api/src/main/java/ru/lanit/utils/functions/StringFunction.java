package ru.lanit.utils.functions;

import io.cucumber.core.exception.CucumberException;

import java.util.List;
import java.util.Random;

public class StringFunction implements Function {

    private static StringFunction stringFunction;
    private static final String LETTERS = "АБВГДЕЁЖЗИЙЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдуёжзийклмнопрстуфхцчшщъыьэюя";

    private StringFunction() {
    }

    public static StringFunction getInstance() {
        if (stringFunction == null) {
            stringFunction = new StringFunction();
        }
        return stringFunction;
    }

    @Override
    public String execute(List<String> parameterList) {
        Random random = new Random();
        if (parameterList.size() == 1) {
            int length = Integer.parseInt(parameterList.get(0));
            char[] text = new char[length];
            for (int i = 0; i < length; i++) {
                text[i] = LETTERS.charAt(random.nextInt(LETTERS.length()));
            }
            return new String(text);
        } else {
            throw new CucumberException("Неверно указаны параметры функции " + parameterList);
        }
    }
}
