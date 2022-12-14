package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;

public class RequestSpecificationExample {

    //Creamos una variable de especificaciones de requerimientos
    //Al usar la variable por defecto, no necesitamos crear una
    RequestSpecification requestSpecification;
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

        //En las especificaciones de solicitud podemos usar la variable por defecto
        RestAssured.requestSpecification = requestSpecBuilder.build();
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

    //Podemos consultar las especificaciones de la solicitud
    @Test
    public void querytest() {
        //Creamos un objeto 'QueryableRequestSpecification'
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier
                .query(requestSpecification);
        //Imprimimos los datos requeridos
        System.out.println(queryableRequestSpecification.getBaseUri());
        System.out.println(queryableRequestSpecification.getHeaders());
    }
}
