package com.company.mz.restassured;

import com.company.mz.restassured.entities.Item;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;


public class RestTest {

    @Test
    public void checkItemTitle(){
        when()
                .get("http://localhost:8189/rest_service/api/v1/items")
                .then()
                .body("[1].title", equalTo("Knife"));

    }

    @Test
    public void testGetItemsResponseCode(){
        when()
                .get("http://localhost:8189/rest_service/api/v1/items")
                .then()
                .statusCode(200);
    }
//change
    @Test
    public void testWithParams(){
        given()
                .param("a", 1)
                .param("b", 5)
        .when()
                .get("http://localhost:8189/rest_service/api/v1/math/sum")
                .then()
                .statusCode(200);
    }

    @Test
    public void testWhithPathParam(){
        given()
                .pathParam("id", 1)
                .when()
                .get("http://localhost:8189/rest_service/api/v1/items/{id}")
                .then()
//                .extract().response().body().print();
                .body("title", equalTo("Gun"));
    }

    @Test
    public void testUserWithParam(){
        given()
                .pathParam("id", 1)
                .when()
                .get("http://localhost:8189/rest_service/api/v1/clients/{id}")
                .then()
                .body("items[0].title", equalTo("Gun"));
    }

    @Test
    public void testAddItem(){
        given()
//                .header("content-type", "application/json")
//                .header("accept", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"title\": \"Toy\"}")
                .post("http://localhost:8189/rest_service/api/v1/items")
                .then()
                .body("title", equalTo("Toy"));
    }
    @Test
    public void testAddItemFromObject(){
        Item item = new Item();
        item.setTitle("JavaObj");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(item)
                .post("http://localhost:8189/rest_service/api/v1/items")
                .then()
                .body("id", notNullValue());
    }
    RequestSpecification baseSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setBasePath("rest_service/api/v1/")
            .setPort(8189)
            .build();

    @Test
    public void testWithRequestSpec(){
        given()
                .spec(baseSpec)
                .get("/items")
                .then()
                .extract().response().body().print();
    }






}
