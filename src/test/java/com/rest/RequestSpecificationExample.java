package com.rest;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestSpecificationExample {

    protected ProjectHeaders projectHeaders = new ProjectHeaders();

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
}
