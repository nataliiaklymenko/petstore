package com.nataliia.klymenko.petstore;

import com.nataliia.klymenko.petstore.restassured.RestAssuredService;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;


import java.util.Map;
import java.util.stream.Stream;

import static com.nataliia.klymenko.petstore.PetStoreTags.REGRESSION;
import static com.nataliia.klymenko.petstore.constants.Endpoint.INVENTORY;
import static com.nataliia.klymenko.petstore.constants.Endpoint.V2;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryTest extends TestBase {

    @Autowired
    private RestAssuredService restAssuredService;

    @Tag(REGRESSION)
    @Test
    public void shouldGetInventory() {
        RequestSpecification requestSpecification = restAssuredService.getRequestSpecification(petStoreBaseUrl);
        var getInventoryResponse = restAssuredService.getResponse(requestSpecification, Method.GET
                , V2 + INVENTORY
                , HttpStatus.OK
                , ContentType.JSON);
        Map<String, Object> responseMap = getInventoryResponse.jsonPath().getMap("$");
        for (Map.Entry<String, Object> entry : responseMap.entrySet()) {
            assertThat("Value should be an Integer: " + entry.getKey(),
                    entry.getValue(), instanceOf(Integer.class));
        }
        //TODO: need to get more information about expected result in order to add more precise assertions
        //assertEquals("6", getInventoryResponse.jsonPath().getString("AVAILABLE"));
    }


    private static Stream<Arguments> testData() {
        return Stream.of(Arguments.of("", ""), Arguments.of("1", "2"));
    }

}
