package ru.lanit.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.И;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.lanit.utils.CompareUtils.compare;
import static ru.lanit.utils.VariableUtils.replaceVariablesFromStash;

public class CompareSteps extends BaseSteps {

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
