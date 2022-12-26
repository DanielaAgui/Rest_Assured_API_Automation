package com.rest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestParameters {

    //Los parametros de consulta son pares clave-valor que aparecen despues de '?' en la URL
    @Test
    public void singleQueryParameter() {
        given()
                .baseUri("https://postman-echo.com")
                //Se puede usar 'param' o 'queryParam'
                .param("foo1", "bar1")
                .log().all()
                .when()
                .get("/get")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }
}
