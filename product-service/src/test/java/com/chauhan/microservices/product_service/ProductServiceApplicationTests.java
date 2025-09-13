package com.chauhan.microservices.product_service;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import javax.management.relation.RelationSupport;

//@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {
	//RestAssured -> to make rest calls from our integration test
	//initialize mongoDB test container
	@ServiceConnection//spring boot will auto inject the relevent uri details for our app
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;//whenever the app running it will inject the port
	//set up rest-assured inside this test
	@BeforeEach
	void setup(){
		//set base uri
		RestAssured.baseURI="http://localhost";//+port [as we are using a random port]
		RestAssured.port = port;
	}
	//we have to install our mongoDB test container before running the test
	static{
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
				{
				  "name":"iphone 15",
				  "description":"iphone 15 is a smartphone from Apple",
				  "price":50000
				}
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("iphone 15"))
				.body("description", Matchers.equalTo("iphone 15 is a smartphone from Apple"))
				.body("price", Matchers.equalTo(50000));


	}

}
