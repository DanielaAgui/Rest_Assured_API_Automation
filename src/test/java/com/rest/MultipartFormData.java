package com.rest;

import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class MultipartFormData {

    //Enviar en el body contenido en formato 'form-data'
    @Test
    public void multipartFormData() {
        given()
                .baseUri("https://postman-echo.com/")
                .multiPart("foo1", "bar1")
                .log().all()
                .when()
                .post("/post")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void uploadFileMultipartFormData() {
        //Creamos un objeto tipo 'String' si vamos a enviar el body clave-valor con un json
        String attributes = "{\"name\":\"temp.txt\",\"parent\":{\"id\":\"123456\"}}";

        given()
                .baseUri("https://postman-echo.com/")
                //Si vamos a enviar el body con un archivo
                //Ponemos el nombre del objeto tipo 'File' a crear y luego la ruta del archivo
                .multiPart("file", new File("target/temp.txt"))
                //Enviamos los datos con la clave y valor tipo json, se debe especificar el tipo del objeto
                .multiPart("attributes", attributes, "application/json")
                .log().all()
                .when()
                .post("/post")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }
}
