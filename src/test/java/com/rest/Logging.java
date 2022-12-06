package com.rest;

import io.restassured.config.LogConfig;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class Logging {

    protected ProjectHeaders projectHeaders = new ProjectHeaders();

    @Test
    public void requestResponseLogging() {
        given()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY)
                //Devuelve la solicitud (request)
                .log().all()
                .when()
                .get("/workspaces")
                .then()
                //Devuelve la respuesta (response)
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void logOnlyIfError() {
        given()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY)
                .log().all()
                .when()
                .get("/workspaces")
                .then()
                //Devuelve el cuerpo de la respuesta si hay un error
                .log().ifError()
                .assertThat().statusCode(200);
    }

    @Test
    public void logOnlyIfValidationFails() {
        given()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY)
                //Imprimimos la request si falla la validacion de una prueba
                .log().ifValidationFails()
                .when()
                .get("/workspaces")
                .then()
                //Imprimimos la response si falla la validacion de una prueba
                .log().ifValidationFails()
                .assertThat().statusCode(200);
    }

    @Test
    public void logOnlyIfValidationFailsWithConfig() {
        given()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY)
                //Imprime request y response si falla la validacion de una prueba
                .config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void logsBacklistHeader() {
        given()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY)
                //Agregamos el encabezado a la lista negra, pasamos su clave por parametro
                .config(config.logConfig(LogConfig.logConfig().blacklistHeader("X-Api-Key")))
                .log().all()
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void logsBacklistHeaders() {
        //Creamos una colección con los encabezados
        Set<String> header = new HashSet<String>();
        header.add("X-Api-Key");
        header.add("Accept");

        given()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY)
                //Agregamos los encabezados a la lista negra, pasamos el nombre de la coleccion por parametro
                .config(config.logConfig(LogConfig.logConfig().blacklistHeaders(header)))
                .log().all()
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200);
    }
}
