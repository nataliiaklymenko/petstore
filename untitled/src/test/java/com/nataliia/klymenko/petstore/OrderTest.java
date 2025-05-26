package com.nataliia.klymenko.petstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nataliia.klymenko.petstore.order.Order;
import com.nataliia.klymenko.petstore.order.OrderStatus;
import com.nataliia.klymenko.petstore.restassured.RestAssuredService;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.Instant;

import static com.nataliia.klymenko.petstore.constants.Endpoint.*;
import static com.nataliia.klymenko.petstore.order.OrderStatus.PLACED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class OrderTest extends TestBase {
    @Autowired
    private RestAssuredService restAssuredService;

    @SneakyThrows
    @Test
    public void shouldPostOrder() {
        var order = Order.builder()
                .id(0L)
                .petId(0L)
                .quantity(10)
                .shipDate(Instant.now().toString())
                .status(PLACED.getValue())
                .complete(false)
                .build();

        RequestSpecification requestSpecification = restAssuredService.getRequestSpecification(petStoreBaseUrl);
        requestSpecification.body(new ObjectMapper().writeValueAsString(order));
        var postOrderResponse = restAssuredService.getResponse(requestSpecification, Method.POST
                , V2 + ORDER
                , HttpStatus.OK
                , ContentType.JSON).as(Order.class);

        assertAll("Order properties",
                () -> assertTrue(postOrderResponse.getId() > 0),
                () -> assertEquals(order.getQuantity(), postOrderResponse.getQuantity()),
                () -> assertFalse(postOrderResponse.isComplete()),
                () -> assertEquals(order.getStatus(), postOrderResponse.getStatus())
        );

    }

    @Test
    public void shouldGetOrder() {
        final int TEST_ORDER_ID = 9;

        RequestSpecification requestSpecification = restAssuredService.getRequestSpecification(petStoreBaseUrl);
        requestSpecification.pathParam("petId", TEST_ORDER_ID);
        var getBuIdOrderResponse = restAssuredService.getResponse(requestSpecification, Method.GET
                , V2 + BY_ID
                , HttpStatus.OK
                , ContentType.JSON).as(Order.class);

        assertAll("Order properties",
                () -> assertTrue(getBuIdOrderResponse.getId() > 0),
                () -> assertEquals(5, getBuIdOrderResponse.getQuantity()),
                () -> assertEquals(TEST_ORDER_ID, getBuIdOrderResponse.getId()),
                () -> assertTrue(getBuIdOrderResponse.isComplete()),
                () -> assertEquals(PLACED.getValue(), getBuIdOrderResponse.getStatus())
        );

    }
}
