package com.rest;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

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

    @Test
    public void multiValueHeaderInTheRequest() {
        given()
                .baseUri("https://0bad87cb-e6b4-4509-86d1-dbc4e36b9340.mock.pstmn.io")
                //Podemos agregar un header con múltiples valores
                .header("multiValueHeader", "value1", "value2")
                .log().all()
                .when()
                .get("/get")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void multiValueHeaderUsingHeaders() {
        //Creamos objetos 'Header' con el mismo nombre y para cada uno de los valores
        Header header1 = new Header("multiValueHeader", "value1");
        Header header2 = new Header("multiValueHeader", "value2");

        //Creamos un objeto 'Headers' para los objetos individuales
        Headers headers = new Headers(header1, header2);

        given()
                .baseUri("https://0bad87cb-e6b4-4509-86d1-dbc4e36b9340.mock.pstmn.io")
                //Llamamos los headers creados con el metodo 'headers'
                .headers(headers)
                .log().all()
                .when()
                .get("/get")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void multiValueHeaderUsingMap() {
        //Creamos un 'HashMap' vacio
        HashMap<String, String> headers = new HashMap<>();
        //Agregamos los elementos al Map
        headers.put("header", "value1");
        headers.put("header", "value2");

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

    @Test
    public void assertResponseHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header");

        given()
                .baseUri("https://0bad87cb-e6b4-4509-86d1-dbc4e36b9340.mock.pstmn.io")
                .headers(headers)
                .when()
                .get("/get")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                //Podemos verificar la respuesta de varios o un solo header
                //.headers("responseHeader", "resValue2")
                .headers("responseHeader", "resValue2",
                        "X-RateLimit-Limit", "120");
    }

    @Test
    public void extractResponseHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header");

        //Creamos un objeto 'Headers'
        Headers extractedHeaders = given()
                .baseUri("https://0bad87cb-e6b4-4509-86d1-dbc4e36b9340.mock.pstmn.io")
                .headers(headers)
                .when()
                .get("/get")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                //Extraemos la respuesta de los headers
                .extract().headers();

        //Obtenemos el nombre de la respuesta del header dado y lo imprimimos
        System.out.println("header name: " + extractedHeaders.get("responseHeader").getName());
        //Obtenemos el valor de la respuesta del header dado y lo imprimimos
        System.out.println("header value: " + extractedHeaders.get("responseHeader").getValue());
    }

    @Test
    public void extractResponseHeadersWithFor() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header");

        //Creamos un objeto 'Headers'
        Headers extractedHeaders = given()
                .baseUri("https://0bad87cb-e6b4-4509-86d1-dbc4e36b9340.mock.pstmn.io")
                .headers(headers)
                .when()
                .get("/get")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                //Extraemos la respuesta de los headers
                .extract().headers();

        //Itera sobre todos los headers obtenidos
        for (Header header : extractedHeaders) {
            //Imprimos el nombre y valor
            System.out.println("Header name: " + header.getName());
            System.out.println("Header value: " + header.getValue());
        }
    }

    @Test
    public void extractMultiValueResponseHeader() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header");

        //Creamos un objeto 'Headers'
        Headers extractedHeaders = given()
                .baseUri("https://0bad87cb-e6b4-4509-86d1-dbc4e36b9340.mock.pstmn.io")
                .headers(headers)
                .when()
                .get("/get")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().headers();

        //Devolvemos una lista con los valores del header dado
        List<String> values = extractedHeaders.getValues("multiValueHeader");

        //Iteramos sobre la lista de valores
        for (String value : values) {
            //Imprimimos los valores
            System.out.println(value);
        }
    }
}
