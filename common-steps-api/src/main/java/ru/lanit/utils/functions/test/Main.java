package ru.lanit.utils.functions.test;

import ru.lanit.steps.ParameterTypes;

public class Main {

    public static void main(String[] args) {
        ParameterTypes parameterTypes = new ParameterTypes();
        System.out.println(parameterTypes.function("ДАТА(сегодня минус 1 день, dd.MM.yyyy HH:mm)"));
        System.out.println(parameterTypes.function("ДАТА(сегодня)"));
        System.out.println(parameterTypes.function("ДАТА(сегодня минус 1 день)"));
        System.out.println(parameterTypes.function("ДАТА(сегодня, dd.MM.yyyy HH:mm)"));

        System.out.println(parameterTypes.function("ЧИСЛО(2)"));
    }
}
