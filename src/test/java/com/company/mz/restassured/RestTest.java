package com.company.mz.restassured;

import com.company.mz.restassured.entities.Item;
import com.company.mz.restassured.repositories.ItemRepository;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class RestTest {
    @Autowired
    private ItemRepository itemRepository;

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
    public void testWithPathParam(){
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

    @Test
    public void testWithParamsMath() {
        String mathResult = given()
                .param("a", "39")
                .param("b", "3")
                .spec(baseSpec)
                .when()
                .get("/math")
                .then()
                .extract().response().body().asString();

        Assertions.assertEquals("42", mathResult);
    }

    @ParameterizedTest
    @DisplayName("check MathController")
    @MethodSource("mathNumber")
    public void checkSum( int a, int b){
        given()
                .spec(baseSpec)
                .param("a", a)
                .param("b", b)
                .get("/math/sum/")
                .then()
                .extract().response().body().equals(a+b);
    }

    @ParameterizedTest
    @DisplayName("check MathController")
    @MethodSource("mathNumber")
    public void checkMultiply( int a, int b){
        given()
                .spec(baseSpec)
                .param("a", a)
                .param("b", b)
                .get("/math/multiply/")
                .then()
//                .extract().response().body().print();
                .extract().response().body().equals(a*b);
    }

    public static Stream<Integer[]> mathNumber(){
        return Stream.of(
                new Integer[]{1, 2},
                new Integer[]{6, 5},
                new Integer[]{100,200},
                new Integer[]{1000, 1},
                new Integer[]{500, 3}
                );
    }

    @Test
    public void checkPutItemRequest(){
        Item item = new Item();
        item.setTitle("Crime plan");

        given()
                .spec(baseSpec)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(item)
                .put("/items")
                .then()
                .statusCode(500);
    }
}
