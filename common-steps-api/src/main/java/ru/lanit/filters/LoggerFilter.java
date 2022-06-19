package ru.lanit.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.LogDetail;
import io.restassured.internal.print.RequestPrinter;
import io.restassured.internal.print.ResponsePrinter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lanit.allure.AllureAttachments;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashSet;

public class LoggerFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(LoggerFilter.class);
    private final HashSet<String> blacklistedHeaders = new HashSet<>();
    private final PrintStream dummy = new PrintStream(OutputStream.nullOutputStream());

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        String prettyPrint = RequestPrinter.print(requestSpec, requestSpec.getMethod(), requestSpec.getURI(), LogDetail.ALL, blacklistedHeaders, dummy, true);
        AllureAttachments.attachTxt(requestSpec.getMethod() + " " + requestSpec.getBasePath(), prettyPrint);
        LOG.info("ОТПРАВЛЕН ЗАПРОС: \n {} \n", prettyPrint);

        Response response = ctx.next(requestSpec, responseSpec);
        prettyPrint = ResponsePrinter.print(response, response, dummy, LogDetail.ALL, true, blacklistedHeaders);
        AllureAttachments.attachTxt(response.getStatusLine(), prettyPrint);
        LOG.info("ПОЛУЧЕН ОТВЕТ: \n {} \n", prettyPrint);
        return response;
    }
}
