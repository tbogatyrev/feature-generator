package ru.lanit.utils.functions;

import java.util.List;

public interface Function {

    /**
     * Метод, вызываемый при выполнении функции.
     *
     * @param parameterList список аргументов функции.
     * @return результат функции в виде строки.
     */
    String execute(List<String> parameterList);
}
