package com.test.tokoko.module.order.payload;

import lombok.Data;

@Data
public class OrderRequest {
    private long userId;
    private long productId;
    private long totalPrice;
    private int amount;
    private int isCancel;
}
