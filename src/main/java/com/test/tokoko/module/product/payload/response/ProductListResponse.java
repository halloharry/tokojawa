package com.test.tokoko.module.product.payload.response;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class ProductListResponse {
    private List<ProductResponse> productResponses = new LinkedList<>();
}
