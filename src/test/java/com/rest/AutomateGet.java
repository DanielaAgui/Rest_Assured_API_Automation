package com.rest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

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

    @Test
    public void extractSingleValueFromResponseWithJson() {
        Response response = given()
                .baseUri("https://api.postman.com")
                .header(headers.HEADER_ACCESSKEY)
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
                .header(headers.HEADER_ACCESSKEY)
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
                .header(headers.HEADER_ACCESSKEY)
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
                .header(headers.HEADER_ACCESSKEY)
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
                .header(headers.HEADER_ACCESSKEY)
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
                .header(headers.HEADER_ACCESSKEY)
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
                .header(headers.HEADER_ACCESSKEY)
                .log().all()
                .when()
                .get("/workspaces")
                .then()
                //Devuelve el cuerpo de la respuesta si hay un error
                .log().ifError()
                .assertThat().statusCode(200);
    }
}
