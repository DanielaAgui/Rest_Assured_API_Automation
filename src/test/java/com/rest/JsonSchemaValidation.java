package com.rest;

import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;

public class JsonSchemaValidation {

    //https://www.liquid-technologies.com/online-json-to-schema-converter
    @Test
    public void validateJsonSchema() {
        given()
                .baseUri("https://postman-echo.com/")
                .log().all()
                .when()
                .post("/get")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .body(matchesJsonSchemaInClasspath("src/main/resources/EchoGet.json"));
    }
}
