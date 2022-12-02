package com.rest;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestSpecificationExample {

    //Creamos una variable de especificaciones de requerimientos
    RequestSpecification requestSpecification;
    protected ProjectHeaders projectHeaders = new ProjectHeaders();

    //Creamos un metodo con las especificaciones
    @BeforeClass
    public void beforeClass() {
        requestSpecification = given()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY);
    }

    @Test
    public void validateStatusCode() {
        //Creamos una variable de especificaciones de solicitud
        RequestSpecification requestSpecification = given()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY);

        given()
                //Llamamos las especificaciones para la solicitud
                .spec(requestSpecification)
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void validateResponseBody() {
        given()
                //Llamamos las especificaciones para la solicitud
                .spec(requestSpecification)
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }
}
