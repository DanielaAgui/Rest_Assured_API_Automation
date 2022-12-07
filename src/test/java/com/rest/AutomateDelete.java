package com.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class AutomateDelete {

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
    public void validateDeleteRequest() {
        String workspaceId = "60841d4a-b532-468c-acbe-4ba328d21d7f";
        given()
                .spec(requestSpecification)
                .pathParam("workspaceId", workspaceId)
                .when()
                //Usamos el metodo 'delete' con el endpoint
                //Con 'delete' eliminamos un recurso
                //Usamos un parametro de ruta en el endpoint
                .delete("/workspaces/{workspaceId}")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .body("workspace.id", equalTo(workspaceId));
    }
}
