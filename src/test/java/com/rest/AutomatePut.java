package com.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class AutomatePut {

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
    public void validatePutRequest() {
        //Podemos pasar datos como parametros en los endpoint
        //Creamos un string con el dato a pasar
        String workspaceId = "1f52326a-98ad-42d9-88ee-c0c1a15414f6";

        //Creamos una variable tipo 'String' para pegar el cuerpo solicitado en el post con los datos modificados
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"myNewWorkspace\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This is created by Rest Assured\"\n" +
                "    }\n" +
                "}";
        given()
                .spec(requestSpecification)
                .body(payload)
                //Creamos un parametro de ruta con el metodo 'pathParam()'
                //Pasamos el nombre del parametro y luego el dato del parametro
                .pathParam("workspaceId", workspaceId)
                .when()
                //Usamos el metodo 'put' con el endpoint
                //Con 'put' modificamos los datos de un elemento actual
                //Usamos el parametro de ruta creado en el endpoint entre llaves | {parametro}
                .put("/workspaces/{workspaceId}")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .body("workspace.name", equalTo("myNewWorkspace"));
    }
}
