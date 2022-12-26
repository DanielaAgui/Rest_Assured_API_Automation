package com.rest;

import org.testng.annotations.Test;

import java.util.HashMap;

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

    @Test
    public void multipleQueryParameters() {
        //Creamos un 'HashMap' con los pares de los parametros
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("foo1", "bar1");
        queryParams.put("foo2", "bar2");

        given()
                .baseUri("https://postman-echo.com")
                //Se puede usar 'params' o 'queryParams'
                .queryParams(queryParams)
                .log().all()
                .when()
                .get("/get")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void multiValueQueryParameter() {
        given()
                .baseUri("https://postman-echo.com")
                //Podemos enviar un parametro con multiples valores
                .queryParam("foo1", "bar1, bar2, bar3")
                .log().all()
                .when()
                .get("/get")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    //Los parametros de ruta permiten hacen parte de la URL y permiten consultar por fragmentos
    @Test
    public void pathParameter() {
        given()
                .baseUri("https://reqres.in")
                //Creamos un parametro de ruta con un nombre de variable y su valor
                .pathParam("userId", "2")
                .log().all()
                .when()
                //En el endpoint usamos la variable que puede ser modificada segun lo que se desea consultar
                .get("/api/users/{userId}")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }
}
