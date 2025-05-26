package com.nataliia.klymenko.petstore.services.restassured;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RestAssuredService {
    private Environment environment;

    @Autowired
    public RestAssuredService(Environment environment) {
        this.environment = environment;
    }

    public Response getResponse(RequestSpecification reqSpec, Method method, String endPoint, HttpStatus httpStatus,
                                       ContentType contentType) {
        log.info("Sending request: {}", endPoint);
        return RestAssured.given()
                .spec(reqSpec)
                .contentType(contentType)
                .log()
                .all()
                .when()
                .request(method, endPoint)
                .then()
                .log()
                .all()
                .contentType(contentType)
                .statusCode(httpStatus.value())
                .extract()
                .response();
    }

    public RequestSpecification getRequestSpecification(String baseUri, RestAssuredConfig config, String type) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(type)
                .setConfig(config)
                .build();


    }

    public RequestSpecification getRequestSpecification(String baseUri) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .build();
    }
}
