package com.rest;

import io.restassured.config.EncoderConfig;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class FormData {

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
                //Enviamos los datos con la clave y valor tipo json
                //Se debe especificar el tipo del objeto Json, ya que el predeterminado en texto plano
                .multiPart("attributes", attributes, "application/json")
                .log().all()
                .when()
                .post("/post")
                .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void formUrlEncoded() {
        given()
                .baseUri("https://postman-echo.com/")
                //En caso de que aparezca 'charset=ISO-8859-1' configuramos el requerimiento
                .config(config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .formParam("key1", "value1")
                .formParam("key 2", "value 2")
                .log().all()
                .when()
                .post("/post")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
}
