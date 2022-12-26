package com.rest;

import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;

public class DownloadFile {

    //Podemos descargar un archivo automaticamente
    @Test
    public void downloadFile() throws IOException {
        //Creamos un objeto para leer el archivo que se va a descargar
        InputStream is = given()
                .baseUri("https://github.com")
                .log().all()
                .when()
                .get("/appium-boneyard/sample-code/raw/master/sample-code/apps/ApiDemos/bin/ApiDemos-debug.apk")
                .then()
                .log().all()
                .extract()
                //Extraemos la respuesta como 'InputStream'
                .response().asInputStream();

        //Creamos un objeto para escribir el archivo descargado
        OutputStream os = new FileOutputStream(new File("ApiDemos-debug.apk"));
        //Creamos una matriz de tipo 'byte' donde pasamos los bytes disponibles obtenidos de la lectura del archivo
        byte[] bytes = new byte[is.available()];
        //Leemos el archivo de entrada
        is.read(bytes);
        //Escribimos el archivo de salida
        os.write(bytes);
        //Cerramos el archivo de salida
        os.close();
    }
}
