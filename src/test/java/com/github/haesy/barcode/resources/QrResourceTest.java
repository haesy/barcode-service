package com.github.haesy.barcode.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.github.haesy.barcode.resources.TestConstants.*;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import com.github.haesy.barcode.resources.QrResource.InputMode;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class QrResourceTest
{
    private static final String PATH = "/qr";

    @Test
    public void testNumeric()
    {
        given().queryParam(CONTENT, "1").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "12").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "123").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "0123456789").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE,
                IMAGE_PNG);
    }

    @Test
    public void testAlphaNumeric()
    {
        given().queryParam(CONTENT, "A").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "AB").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "ABC").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "ABC123").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
    }

    @Test
    public void testKana()
    {
        given().queryParam(CONTENT, "あ").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "はは").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "日本語").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
    }

    @Test
    public void testUmlauts()
    {
        // encodable by ISO-8859-1
        given().queryParam(CONTENT, "ÄäÖöÜüß").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
        // encodable by UTF-8
        given().queryParam(CONTENT, "ÄäÖöÜüẞß").when().get(PATH).then().statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
    }

    @Test
    public void testUrl()
    {
        given().queryParam(CONTENT, "http://www.example.com").when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "https://www.example.com").when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "https://www.example.com/foo-bar/baz_foo?a=b&answer=42").when().get(PATH).then()
                .statusCode(200).header(CONTENT_TYPE, IMAGE_PNG);
    }

    @Test
    public void testMargin()
    {
        given().queryParam(CONTENT, "margin").queryParam(MARGIN, 0).when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "margin").queryParam(MARGIN, 1).when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "margin").queryParam(MARGIN, 2).when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);
    }

    @Test
    public void testVersion()
    {
        given().queryParam(CONTENT, "VERSION").queryParam(VERSION, 1).when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "VERSION").queryParam(VERSION, 40).when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);

        given().queryParam(CONTENT, "VERSION").queryParam(VERSION, 0).when().get(PATH).then().statusCode(400)
                .header(CONTENT_TYPE, TEXT_PLAIN);
        given().queryParam(CONTENT, "VERSION").queryParam(VERSION, 41).when().get(PATH).then().statusCode(400)
                .header(CONTENT_TYPE, TEXT_PLAIN);
    }

    @Test
    public void testErrorCorrection()
    {
        given().queryParam(CONTENT, "ERROR").queryParam(ERROR_CORRECTION, "L").when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "ERROR").queryParam(ERROR_CORRECTION, "M").when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "ERROR").queryParam(ERROR_CORRECTION, "Q").when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);
        given().queryParam(CONTENT, "ERROR").queryParam(ERROR_CORRECTION, "H").when().get(PATH).then().statusCode(200)
                .header(CONTENT_TYPE, IMAGE_PNG);

        given().queryParam(CONTENT, "ERROR").queryParam(ERROR_CORRECTION, "FOO").when().get(PATH).then().statusCode(400)
                .header(CONTENT_TYPE, TEXT_PLAIN);
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

    @Test
    public void testInputMode()
    {
        assertEquals(InputMode.NUMERIC, InputMode.forString("0123456789"));
        assertEquals(InputMode.ALPHANUMERIC, InputMode.forString("ABC"));
        assertEquals(InputMode.ALPHANUMERIC, InputMode.forString("ABC123"));
        assertEquals(InputMode.BINARY, InputMode.forString("abc"));
        assertEquals(InputMode.BINARY, InputMode.forString("abc123"));
        assertEquals(InputMode.BINARY, InputMode.forString("ÄäÖöÜüß"));
        assertEquals(InputMode.KANJI_KANA, InputMode.forString("あ"));
        assertEquals(InputMode.KANJI_KANA, InputMode.forString("日本語"));
        assertEquals(InputMode.UTF_8, InputMode.forString("ÄäÖöÜüẞß"));
    }
}
