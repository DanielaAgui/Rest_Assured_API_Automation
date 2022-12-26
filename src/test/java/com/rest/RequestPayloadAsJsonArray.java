package com.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class RequestPayloadAsJsonArray {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    protected ProjectHeaders projectHeaders = new ProjectHeaders();

    @BeforeClass
    public void beforeClass() {
        requestSpecification = given()
                .baseUri("https://0bad87cb-e6b4-4509-86d1-dbc4e36b9340.mock.pstmn.io")
                .header("x-mock-match-request-body", "true")
                //Podemos configurar la codificación UTF-8
                //.config(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))

                //O podemos pasarlo como parametro en 'contentType()', recordar que hay que ponerlo como 'header' en Postman
                //.contentType("application/json;charset=utf-8")

                .contentType(ContentType.JSON)
                .log().all();

        responseSpecification = RestAssured.expect()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all();
    }

    @Test
    public void validatePostRequestPayloadFromFile() {
        //Creamos un 'HashMap' por cada objeto del json
        //Los objetos son las lineas entre llaves {}
        HashMap<String, String> obj1 = new HashMap<String, String>();
        obj1.put("id", "5001");
        obj1.put("type", "None");

        HashMap<String, String> obj2 = new HashMap<String, String>();
        obj2.put("id", "5002");
        obj2.put("type", "Glazed");

        //Creamos una lista con los objetos del json
        //Recibimos 'Map' ya que es lo que usamos para representar los objetos Json
        List<Map> jsonList = new ArrayList<>();
        jsonList.add(obj1);
        jsonList.add(obj2);

        given()
                .spec(requestSpecification)
                //Enviamos al body la lista creada
                .body(jsonList)
                .when()
                .post("/post")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .body("msg", equalTo("Success"));
    }
}

