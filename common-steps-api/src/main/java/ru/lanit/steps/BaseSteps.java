package ru.lanit.steps;


import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class BaseSteps {

    protected static final Logger LOGGER = LogManager.getLogger(RequestSteps.class);

    public RequestSpecification getRequestSpecification() {
        return given();
    }
}
