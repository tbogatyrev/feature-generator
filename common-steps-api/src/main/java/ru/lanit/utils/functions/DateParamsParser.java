package ru.lanit.utils.functions;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class DateParamsParser {

    private final List<String> params;

    public DateParamsParser(List<String> params) {
        this.params = params;
    }

    public DateParamsModel parse() {
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

    private ChronoUnit getChronoUnit(String desc) {
        return Arrays.stream(OffsetUnit.values())
                .filter(value -> desc.startsWith(value.getIdent()))
                .findFirst()
                .map(OffsetUnit::getChronoUnit)
                .orElse(null);
    }
}
