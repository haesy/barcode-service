package com.github.haesy.barcode.resources;

import static com.github.haesy.barcode.resources.TestConstants.*;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class EAN13ResourceTest
{
    private static final String PATH = "/ean-13";

    @Test
    public void testValid()
    {
        given().queryParam(CONTENT, "123456789012").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE,
                IMAGE_PNG);
    }

    @Test
    public void testInvalid()
    {
        given().queryParam(CONTENT, "1").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE, TEXT_PLAIN);
        given().queryParam(CONTENT, "123").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE, TEXT_PLAIN);
        given().queryParam(CONTENT, "12345").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE, TEXT_PLAIN);
        given().queryParam(CONTENT, "123456").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE, TEXT_PLAIN);
        given().queryParam(CONTENT, "12345678").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE,
                TEXT_PLAIN);
        given().queryParam(CONTENT, "abcdefg").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE, TEXT_PLAIN);
    }

    @Test
    public void testMargin()
    {
        given().queryParam(CONTENT, "123456789012").queryParam(MARGIN, 0).when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "123456789012").queryParam(MARGIN, 1).when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "123456789012").queryParam(MARGIN, 2).when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);
    }

    @Test
    public void testContentEmpty()
    {
        given().queryParam(CONTENT, "").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE, TEXT_PLAIN);
    }

    @Test
    public void testContentMissing()
    {
        given().when().get(PATH).then().statusCode(400).header(CONTENT_TYPE, TEXT_PLAIN);
    }
}
