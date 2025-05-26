package com.nataliia.klymenko.petstore.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nataliia.klymenko.petstore.order.Order;
import com.nataliia.klymenko.petstore.services.restassured.RestAssuredService;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.nataliia.klymenko.petstore.constants.Endpoint.ORDER;
import static com.nataliia.klymenko.petstore.constants.Endpoint.V2;
import static com.nataliia.klymenko.petstore.order.OrderStatus.PLACED;

@Service
public class OrderService {
    @Value("${petstore.base.url}")
    private String petStoreBaseUrl;

    private final RestAssuredService restAssuredService;

    public OrderService(@Value("${petstore.base.url}") String petStoreBaseUrl, RestAssuredService restAssuredService) {
        this.petStoreBaseUrl = petStoreBaseUrl;
        this.restAssuredService = restAssuredService;
    }

    public Long createOrder(Long id, Long petId, Integer quantity, String date) throws JsonProcessingException {
        var order = Order.builder()
                .id(id)
                .petId(petId)
                .quantity(quantity)
                .shipDate(date)
                .status(PLACED.getValue())
                .complete(false)
                .build();

        RequestSpecification requestSpecification = restAssuredService.getRequestSpecification(petStoreBaseUrl);
        requestSpecification.body(new ObjectMapper().writeValueAsString(order));
        return restAssuredService.getResponse(requestSpecification, Method.POST
                , V2 + ORDER
                , HttpStatus.OK
                , ContentType.JSON).as(Order.class).getId();
    }
}
