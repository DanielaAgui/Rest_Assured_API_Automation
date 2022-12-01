package com.rest;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class AutomateHeaders {

    @Test
    public void multipleHeaders() {
        given()
                .baseUri("https://0bad87cb-e6b4-4509-86d1-dbc4e36b9340.mock.pstmn.io")
                //Podemos agregar multiples 'headers' en la solicitud, cada uno con su nombre y valor
                .header("header", "value2")
                .header("x-mock-match-request-headers", "header")
                .when()
                .get("/get")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void multipleHeadersObject() {
        //Creamos un objeto 'Header' por cada uno de ellos con su nombre y valor
        Header header = new Header("header", "value2");
        Header matchHeader = new Header("x-mock-match-request-headers", "header");

        given()
                .baseUri("https://0bad87cb-e6b4-4509-86d1-dbc4e36b9340.mock.pstmn.io")
                //Llamamos cada uno con el metodo de 'header'
                .header(header)
                .header(matchHeader)
                .when()
                .get("/get")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void multipleHeadersUsingHeaders() {
        //Creamos un objeto 'Header' para cada 'header' a usar
        Header header = new Header("header", "value2");
        Header matchHeader = new Header("x-mock-match-request-headers", "header");

        //Creamos un objeto 'Headers' donde agregamos los headers creados
        Headers headers = new Headers(header, matchHeader);

        given()
                .baseUri("https://0bad87cb-e6b4-4509-86d1-dbc4e36b9340.mock.pstmn.io")
                //Llamamos la colección de headers con el metodo 'headers'
                .headers(headers)
                .when()
                .get("/get")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void multipleHeadersUsingMap() {
        //Creamos un 'HashMap' vacio
        HashMap<String, String> headers = new HashMap<>();
        //Agregamos los elementos al Map
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header");

        given()
                .baseUri("https://0bad87cb-e6b4-4509-86d1-dbc4e36b9340.mock.pstmn.io")
                //Llamamos la coleccion de headers con el metodo 'headers'
                .headers(headers)
                .when()
                .get("/get")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }
}
