package ru.lanit.utils;

import java.util.HashMap;
import java.util.Map;

import static ru.lanit.utils.storage.Stash.STASH;

public class VariableUtils {

    public static String replaceVariablesFromStash(String expression) {
        if (expression == null) {
            return "";
        } else {
            return replaceVariables(expression, STASH.getAllValues());
        }
    }

    /**
     * замена контекстных переменных на значения
     *
     * @param preBody текст
     * @param vars    контекстные переменные
     * @return значение
     */
    private static String replaceVariables(String preBody, Map<String, Object> vars) {
        StringBuilder replacedText = new StringBuilder(preBody);

        final String patternStartVar = "${";
        final String patternEndVar = "}";

        while (true) {
            int fi = replacedText.indexOf(patternStartVar);
            if (fi == -1) {
                break;
            }
            int li = replacedText.indexOf(patternEndVar, fi);
            String var = replacedText.substring(fi + patternStartVar.length(), li);
            if (vars.containsKey(var)) {
                replacedText.replace(fi, li + 1, vars.get(var).toString());
            } else {
                break;
            }
        }
        return replacedText.toString();
    }

    /**
     * Метод меняет переменные в Map, описанные как ${var} на соотвествующие значения из хранилища(Stash), где ключ соотвествует имени переменной.
     *
     * @param mapWithParams
     * @return Map с замененными значениями
     */
    public static Map<String, String> replaceMapsVariablesFromStash(Map<String, String> mapWithParams) {
        Map<String, String> replacedParams = new HashMap<>();
        mapWithParams.forEach((key, value) -> replacedParams.put(key, replaceVariablesFromStash(value)));
        return replacedParams;
    }
}
