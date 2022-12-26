package com.rest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class MultipartFormData {

    //Enviar en el body contenido en formato 'form-data'
    @Test
    public void multipartFormData() {
        given()
                .baseUri("https://postman-echo.com/")
                .multiPart("foo1", "bar1")
                .log().all()
                .when()
                .post("/post")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }
}
