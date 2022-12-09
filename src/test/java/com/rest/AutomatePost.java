package com.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class AutomatePost {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    protected ProjectHeaders projectHeaders = new ProjectHeaders();

    @BeforeClass
    public void beforeClass() {
        requestSpecification = given()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY)
                .contentType(ContentType.JSON)
                .log().all();

        responseSpecification = RestAssured.expect()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all();
    }

    @Test
    public void validatePostRequest() {
        //Creamos una variable tipo 'String' para pegar el cuerpo solicitado en el post con los nuevos datos
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"myFirstWorkspace\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"Rest Assured created this\"\n" +
                "    }\n" +
                "}";
        given()
                .spec(requestSpecification)
                //Pasamos el body en el cuerpo de la request
                .body(payload)
                .when()
                //Usamos el metodo 'post' con el endpoint
                //Con 'post' creamos un nuevo elemento
                .post("/workspaces")
                .then()
                .spec(responseSpecification)
                .assertThat()
                //Verificamos el nombre
                .body("workspace.name", equalTo("myFirstWorkspace"));
    }

    @Test
    public void validatePostRequestWithFile() {
        //Creamos un archivo con el json del body
        //Creamos un objeto tipo 'File' y añadimos la ruta del archivo creado
        File file = new File("src/main/resources/CreateWorkspacePayload.json");
        given()
                .spec(requestSpecification)
                //Enviamos en el cuerpo del requerimiento el archivo json creado
                .body(file)
                .when()
                .post("/workspaces")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .body("workspace.name", equalTo("myFirstWorkspace"));
    }
}
