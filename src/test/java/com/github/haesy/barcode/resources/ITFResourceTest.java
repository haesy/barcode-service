package com.github.haesy.barcode.resources;

import static com.github.haesy.barcode.resources.TestConstants.*;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ITFResourceTest
{
    private static final String PATH = "/itf";

    @Test
    public void testValid()
    {
        given().queryParam(CONTENT, "12").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "1234").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "123456").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "12345678").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "1234567890").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE,
                IMAGE_PNG);

        given().queryParam(CONTENT, "00000000000000").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE,
                IMAGE_PNG);
        given().queryParam(CONTENT, "99999999999999").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE,
                IMAGE_PNG);
    }

    @Test
    public void testInvalid()
    {
        given().queryParam(CONTENT, "1").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE, TEXT_PLAIN);
        given().queryParam(CONTENT, "123").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE, TEXT_PLAIN);
        given().queryParam(CONTENT, "12345").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE, TEXT_PLAIN);
        given().queryParam(CONTENT, "1234567").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE, TEXT_PLAIN);
        given().queryParam(CONTENT, "123456789").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE,
                TEXT_PLAIN);

        given().queryParam(CONTENT, "0000000000000").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE,
                TEXT_PLAIN);
        given().queryParam(CONTENT, "9999999999999").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE,
                TEXT_PLAIN);

        given().queryParam(CONTENT, "000000000000000").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE,
                TEXT_PLAIN);
        given().queryParam(CONTENT, "999999999999999").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE,
                TEXT_PLAIN);

        given().queryParam(CONTENT, "aaaaaaaaaaaaaa").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE,
                TEXT_PLAIN);
        given().queryParam(CONTENT, "0000000000000a").when().get(PATH).then().statusCode(400).header(CONTENT_TYPE,
                TEXT_PLAIN);
    }

    @Test
    public void testMargin()
    {
        given().queryParam(CONTENT, "00").queryParam(MARGIN, 0).when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "00").queryParam(MARGIN, 1).when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "00").queryParam(MARGIN, 2).when().get(PATH).then().statusCode(200)
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
