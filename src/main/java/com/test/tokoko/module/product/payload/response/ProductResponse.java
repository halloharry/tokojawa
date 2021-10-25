package com.test.tokoko.module.product.payload.response;

import lombok.Data;

@Data
public class ProductResponse {
    private String name;
    private double price;
    private int amount;
}
