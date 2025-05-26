package com.nataliia.klymenko.petstore.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private Long id;
    private Long petId;
    private Integer quantity;
    private String shipDate;
    private String status;
    private boolean complete;
}
