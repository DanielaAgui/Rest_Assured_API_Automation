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

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class RequestPayloadComplexJson {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    protected ProjectHeaders projectHeaders = new ProjectHeaders();

    @BeforeClass
    public void beforeClass() {
        requestSpecification = given()
                .baseUri("https://0bad87cb-e6b4-4509-86d1-dbc4e36b9340.mock.pstmn.io")
                .header(projectHeaders.HEADER_ACCESSKEY)
                .header("x-mock-match-request-body", "true")
                .contentType(ContentType.JSON)
                .log().all();

        responseSpecification = RestAssured.expect()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all();
    }

    @Test
    public void validatePostRequestPayloadComplexJson() {

        /*{
            "id": "0001",
                "type": "donut",
                "name": "Cake",
                "ppu": 0.55,
                "batters":
            {
                "batter":
				[
                { "id": "1001", "type": "Regular" },
                { "id": [5, 9],
                   "type": "Chocolate"
                }
				]
            },
            "topping":
		[
            { "id": "5001", "type": "None" },
            { "id": "5002",
                    "type": ["test1", "test2"]
            }
		]
        }*/

        //Creamos los colecciones para cada objeto del json desde el mas interno al mas externo
        List<Integer> idArrayList = new ArrayList<>();
        idArrayList.add(5);
        idArrayList.add(9);

        HashMap<String, Object> batterHashMap2 = new HashMap<>();
        batterHashMap2.put("id", idArrayList);
        batterHashMap2.put("type", "Chocolate");

        HashMap<String, Object> batterHashMap1 = new HashMap<>();
        batterHashMap1.put("id", "1001");
        batterHashMap1.put("type", "Regular");

        List<HashMap<String, Object>> batterArrayList = new ArrayList<>();
        batterArrayList.add(batterHashMap1);
        batterArrayList.add(batterHashMap2);

        HashMap<String, List<HashMap<String, Object>>> battersHashMap = new HashMap<>();
        battersHashMap.put("batter", batterArrayList);

        List<String> typeArrayList = new ArrayList<>();
        typeArrayList.add("test1");
        typeArrayList.add("test2");

        HashMap<String, Object> toppingHashMap2 = new HashMap<>();
        toppingHashMap2.put("id", "5002");
        toppingHashMap2.put("type", typeArrayList);

        HashMap<String, Object> toppingHashMap1 = new HashMap<>();
        toppingHashMap1.put("id", "5001");
        toppingHashMap1.put("type", "None");

        List<HashMap<String, Object>> toppingArrayList = new ArrayList<>();
        toppingArrayList.add(toppingHashMap1);
        toppingArrayList.add(toppingHashMap2);

        HashMap<String, Object> mainHashMap = new HashMap<>();
        mainHashMap.put("id", "0001");
        mainHashMap.put("type", "donut");
        mainHashMap.put("name", "Cake");
        mainHashMap.put("ppu", 0.55);
        mainHashMap.put("batters", battersHashMap);
        mainHashMap.put("topping", toppingArrayList);

        given()
                .spec(requestSpecification)
                .body(mainHashMap)
                .when()
                .post("/postComplexJson")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .body("msg", equalTo("Success"));
    }
}
