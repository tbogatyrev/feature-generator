package ru.lanit.utils.functions.test;

import ru.lanit.steps.ParameterTypes;
import ru.lanit.utils.storage.Stash;

public class Main {

    public static void main(String[] args) {
        ParameterTypes parameterTypes = new ParameterTypes();
        System.out.println(parameterTypes.smartString("ДАТА(сегодня минус 1 день, dd.MM.yyyy HH:mm)"));
        System.out.println(parameterTypes.smartString("ДАТА(сегодня)"));
        System.out.println(parameterTypes.smartString("ДАТА(сегодня минус 1 день)"));
        System.out.println(parameterTypes.smartString("ДАТА(сегодня, dd.MM.yyyy HH:mm)"));

        System.out.println(parameterTypes.smartString("ЧИСЛО(2)"));

        Stash.STASH.put("test", "ДАТА(сегодня минус 1 день, dd.MM.yyyy HH:mm)");
        System.out.println(parameterTypes.smartString("${test}"));
    }
}
