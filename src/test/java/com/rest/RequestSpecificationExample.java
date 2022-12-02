package com.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;

public class RequestSpecificationExample {

    //Creamos una variable de especificaciones de requerimientos
    RequestSpecification requestSpecification;
    RequestSpecification requestSpecificationBuilder;
    protected ProjectHeaders projectHeaders = new ProjectHeaders();

    //Creamos un metodo con las especificaciones
    @BeforeClass
    public void beforeClass() {
        //Se pueden establecer con 'with()' o 'given()'
        requestSpecification = with()
                .baseUri("https://api.postman.com")
                .header(projectHeaders.HEADER_ACCESSKEY);
    }

    //Otra forma de crear las especificaciones de solicitudes
    @BeforeClass
    public void withRequestSpecBuilder() {
        //Creamos un objeto 'RequestSpecBuilder'
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        //Añadimos los requerimientos
        requestSpecBuilder.setBaseUri("https://api.postman.com");

        //Construimos los requerimientos con la variable creada
        requestSpecificationBuilder  = requestSpecBuilder.build();
    }

    @Test
    public void requestSpecificationWithObject() {
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
    public void requestSpecificationWithMethod() {
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
    public void requestSpecBuilder() {
        //Llamamos los requerimientos en el metodo 'given()'
        given(requestSpecification)
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }
}
