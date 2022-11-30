package com.rest;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class AutomateGet {

    protected Headers headers = new Headers();

    @Test
    public void validateGetStatusCode() {
        //Dado
        given()
                //Una ruta de solicitud
                .baseUri("https://api.postman.com")
                //Encabezado de una solicitud, en este caso es la API Key
                //Se representan con un par clave-valor
                .header(headers.HEADER_ACCESSKEY)
                //Cuando
                .when()
                //Llamamos al endpoint (parte final de la URI para acceder)
                .get("/workspaces")
                //Entonces
                .then()
                //Devuelve la respuesta
                .log().all()
                //Y verificamos un codigo de estado de 200
                .assertThat().statusCode(200);
    }

    @Test
    public void validateResponseBody() {
        given()
                .baseUri("https://api.postman.com")
                .header(headers.HEADER_ACCESSKEY)
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                //Verificamos que el JSON en la ubicación dada contenga una coleccion de elementos
                .body("workspaces.name", hasItems("Curso Udemy", "Curso de Postman GeekQA", "Curso Rest Assured API"),
                        //Se pueden hacer varias validaciones al tiempo
                        "workspaces.type", hasItems("personal"));
    }

    @Test
    public void validateResponseBodyCursoUdemy() {
        given()
                .baseUri("https://api.postman.com")
                .header(headers.HEADER_ACCESSKEY)
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                //Verificamos que el dato de la ubicacion especifica del JSON sea igual al dado
                .body("workspaces[1].name", equalTo("Curso de Postman GeekQA"),
                        //Verificamos que el tamaño del JSON sea igual a
                        "workspaces.size()", is(equalTo(3)));
    }

    @Test
    public void extractResponse() {
        //Creamos una variable de tipo 'Response'
        Response response = given()
                .baseUri("https://api.postman.com")
                .header(headers.HEADER_ACCESSKEY)
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                //Extraemos la respuesta
                .extract().response();
        //Imprimimos la respuesta como String
        System.out.println(response.asString());
    }
}
