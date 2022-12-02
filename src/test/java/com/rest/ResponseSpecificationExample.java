package com.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ResponseSpecificationExample {

    //Creamos una variable 'ResponseSpecification'
    ResponseSpecification responseSpecification;
    RequestSpecification requestSpecification;
    protected ProjectHeaders projectHeaders = new ProjectHeaders();

    @BeforeClass
    public void beforeClass() {
        requestSpecification = with()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY);

        //Creamos la respuesta con lo esperado
        responseSpecification = RestAssured.expect()
                .statusCode(200)
                .contentType(ContentType.JSON)
    }

    @Test
    public void responseSpecificationExample1() {
        given()
                .spec(requestSpecification)
                .when()
                .get("/workspaces")
                .then()
                //Llamamos las especificaciones de respuesta
                .spec(responseSpecification)
                .log().all();
    }

    @Test
    public void responseSpecificationExample2() {
        Response response = given()
                .spec(requestSpecification)
                .when()
                .get("/workspaces")
                .then()
                .spec(responseSpecification)
                .log().all()
                .extract().response();
        assertThat(response.path("workspaces[0].name").toString(), equalTo("Curso Udemy"));
    }
}
