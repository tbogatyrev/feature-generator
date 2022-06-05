package ru.lanit.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.И;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.lanit.utils.VariableUtils.replaceVariablesFromStash;

public class CompareSteps extends BaseSteps {

    /**
     * Сравнивает строки и числа
     *
     * @param expected значение 1
     * @param actual   значение 2
     * @param operator оператор
     * @return вернет true или false
     */
    public static boolean compare(String expected, String actual, String operator) {
        expected = expected == null ? "null" : expected;
        actual = actual == null ? "null" : actual;
        switch (operator.toLowerCase()) {
            case "==":
            case "!=":
                return compareUnknownType(expected, actual, operator);
            case "содержит":
                return expected.contains(actual);
            case "не содержит":
                return !expected.contains(actual);
            case ">":
            case "<":
                return compareNumbers(expected, actual, operator);
            default:
                throw new IllegalArgumentException(String.format("Параметр '%s' не поддерживается", operator));
        }
    }

    private static boolean compareNumbers(String firstValue, String secondValue, String operator) {
        if (!isNumeric(firstValue, secondValue)) {
            throw new IllegalArgumentException("Аргумент не является числом");
        }
        BigDecimal num1 = new BigDecimal(firstValue);
        BigDecimal num2 = new BigDecimal(secondValue);

        switch (operator) {
            case "==":
                return num1.compareTo(num2) == 0;
            case "!=":
                return num1.compareTo(num2) != 0;
            case ">":
                return num1.compareTo(num2) > 0;
            case "<":
                return num1.compareTo(num2) < 0;
            default:
                throw new IllegalArgumentException(String.format("Оператор '%s' не поддерживается", operator));
        }
    }

    private static boolean compareStrings(String firstValue, String secondValue, String operator) {
        switch (operator.toLowerCase()) {
            case "==":
                return firstValue.equals(secondValue);
            case "!=":
                return !firstValue.equals(secondValue);
            default:
                throw new IllegalArgumentException(String.format("Параметр '%s' не поддерживается", operator));
        }
    }

    private static boolean compareUnknownType(String firstValue, String secondValue, String operator) {
        if (isNumeric(firstValue, secondValue)) {
            return compareNumbers(firstValue, secondValue, operator);
        } else {
            return compareStrings(firstValue, secondValue, operator);
        }
    }

    private static boolean isNumeric(String stringNumber) {
        return NumberUtils.isParsable(stringNumber);
    }

    private static boolean isNumeric(String firstValue, String secondValue) {
        return isNumeric(firstValue) && isNumeric(secondValue);
    }

    @И("сравниваю значения")
    public void checkValues(DataTable values) {
        values.asLists().forEach(line -> {
            String actual = replaceVariablesFromStash(line.get(0));
            String operator = line.get(1);
            String expected = replaceVariablesFromStash(line.get(2));
            boolean compareResult = compare(actual, expected, operator);
            LOGGER.info("сравниваются значения: '{} {} {}'", actual, operator, expected);
            assertThat(compareResult).isTrue();
        });
    }
}
