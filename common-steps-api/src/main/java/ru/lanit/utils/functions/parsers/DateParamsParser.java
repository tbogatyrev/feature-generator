package ru.lanit.utils.functions.parsers;

import ru.lanit.utils.functions.OffsetUnit;
import ru.lanit.utils.functions.models.DateParamsModel;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class DateParamsParser {

    public static DateParamsModel parse(List<String> params) {
        String[] dateParams = params.get(0).split("\\s");
        if (params.size() == 1) {
            if (params.get(0).equals("сегодня")) {
                return new DateParamsModel(params.get(0));
            } else {
                return new DateParamsModel(dateParams[0])
                        .setHasOffset(true)
                        .setOperator(dateParams[1])
                        .setOffset(dateParams[2])
                        .setOffsetDescription(getChronoUnit(dateParams[3]));
            }
        } else if (params.size() == 2) {
            if (params.get(0).equals("сегодня")) {
                return new DateParamsModel(dateParams[0])
                        .setHasFormat(true)
                        .setFormat(params.get(1).trim());
            } else {
                return new DateParamsModel(dateParams[0])
                        .setHasOffset(true)
                        .setOperator(dateParams[1])
                        .setOffset(dateParams[2])
                        .setOffsetDescription(getChronoUnit(dateParams[3]))
                        .setHasFormat(true)
                        .setFormat(params.get(1).trim());
            }
        }
        throw new RuntimeException();
    }

    private static ChronoUnit getChronoUnit(String desc) {
        return Arrays.stream(OffsetUnit.values())
                .filter(value -> desc.startsWith(value.getIdent()))
                .findFirst()
                .map(OffsetUnit::getChronoUnit)
                .orElse(null);
    }
}
