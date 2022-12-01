package com.rest;

import io.restassured.config.LogConfig;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class AutomateGet {

    protected ProjectHeaders projectHeaders = new ProjectHeaders();

    @Test
    public void validateGetStatusCode() {
        //Dado
        given()
                //Una ruta de solicitud
                .baseUri("https://api.postman.com")
                //Encabezado de una solicitud, en este caso es la API Key
                //Se representan con un par clave-valor
                .header(projectHeaders.HEADER_ACCESSKEY)
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
                .header(projectHeaders.HEADER_ACCESSKEY)
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
                .header(projectHeaders.HEADER_ACCESSKEY)
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
                .header(projectHeaders.HEADER_ACCESSKEY)
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

    @Test
    public void extractSingleValueFromResponseWithJson() {
        Response response = given()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY)
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response();
        //Creamos una variable de tipo Json y pasamos la respuesta como String
        JsonPath jsonPath = new JsonPath(response.asString());
        //Imprimimos la variable obtenida segun la ubicacion dada
        System.out.println(jsonPath.getString("workspaces[0].name"));
    }

    @Test
    public void extractSingleValueFromResponseWithJsonFrom() {
        //Creamos una variable de tipo String
        String response = given()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY)
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                //Extraemos la respuesta como String
                .extract().response().asString();
        //Obtenemos de la respuesta Json un String segun la ubicacion dada
        System.out.println(JsonPath.from(response).getString("workspaces[0].name"));
    }

    @Test
    public void extractSingleValueFromResponseWithPath() {
        //Creamos una variable de tipo String
        String response = given()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY)
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                //Extraemos la respuesta segun la ubicacion dada
                .extract().response().path("workspaces[0].name");
        //Imprimimos la respuesta
        System.out.println(response);
    }

    @Test
    public void hamcrestAssertOnExtractedResponse() {
        String response = given()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY)
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response().path("workspaces[0].name");
        System.out.println(response);

        //Podemos realizar 'Assertions' con los elementos extraidos de las respuestas
        assertThat(response, equalTo("Curso Udemy"));
    }

    @Test
    public void validateResponseBodyHamcrestLearnings() {
        given()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY)
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("workspaces.name", contains("Curso Udemy", "Curso de Postman GeekQA", "Curso Rest Assured API"));
    }

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
