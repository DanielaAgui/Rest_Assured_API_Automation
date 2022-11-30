package com.rest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;

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
}
