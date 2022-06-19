package ru.lanit.utils.functions;

import ru.lanit.utils.functions.models.DateParamsModel;
import ru.lanit.utils.functions.parsers.DateParamsParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class DateFunction implements Function {

    private static DateFunction dateFunction;
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private DateFunction() {
    }

    public static DateFunction getInstance() {
        if (dateFunction == null) {
            dateFunction = new DateFunction();
        }
        return dateFunction;
    }

    @Override
    public String execute(List<String> parameterList) {
        DateParamsModel paramsModel = DateParamsParser.parse(parameterList);
        if (!paramsModel.hasOffset() && !paramsModel.hasFormat()) {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
        } else if (paramsModel.hasOffset() && !paramsModel.hasFormat()) {
            paramsModel.setFormat(DEFAULT_DATE_FORMAT);
            return shiftDate(paramsModel);
        } else if (!paramsModel.hasOffset() && paramsModel.hasFormat()) {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern(paramsModel.getFormat()));
        } else if (paramsModel.hasOffset() && paramsModel.hasFormat()) {
            return shiftDate(paramsModel);
        } else {
            throw new RuntimeException();
        }
    }

    private String shiftDate(DateParamsModel model) {
        switch (model.getOperator()) {
            case "плюс":
                return LocalDateTime.now()
                        .plus(Long.parseLong(model.getOffset()), requireNonNull(model.getOffsetDescription()))
                        .format(DateTimeFormatter.ofPattern(model.getFormat()));
            case "минус":
                return LocalDateTime.now()
                        .minus(Long.parseLong(model.getOffset()), requireNonNull(model.getOffsetDescription()))
                        .format(DateTimeFormatter.ofPattern(model.getFormat()));
            default:
                throw new IllegalArgumentException("Неверный оператор " + model.getOperator());
        }
    }
}
