package ru.lanit.utils.functions.test;

import ru.lanit.steps.types.DataTableTypes;
import ru.lanit.steps.types.ParameterTypes;
import ru.lanit.utils.storage.Stash;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        ParameterTypes parameterTypes = new ParameterTypes();
        DataTableTypes dataTableTypes = new DataTableTypes();
        System.out.println(parameterTypes.smartString("ДАТА(сегодня минус 5 дней, dd.MM.yyyy HH:mm)"));
        System.out.println(parameterTypes.smartString("ДАТА(сегодня)"));
        System.out.println(parameterTypes.smartString("ДАТА(сегодня минус 1 день)"));
        System.out.println(parameterTypes.smartString("ДАТА(сегодня, dd.MM.yyyy HH:mm)"));

        System.out.println(parameterTypes.smartString("ЧИСЛО(2)"));

        Stash.STASH.put("test", "ДАТА(сегодня минус 1 день, dd.MM.yyyy HH:mm)");
        System.out.println(parameterTypes.smartString("${test}"));

        HashMap<String, String> map = new HashMap<>();
        map.put("test1", "ДАТА(сегодня минус 5 дней, dd.MM.yyyy HH:mm)");
        map.put("test2", "${test}");
        map.put("test3", "ЧИСЛО(2)");

//        System.out.println(dataTableTypes.mapContainer(map).getSmartMap());
    }
}
