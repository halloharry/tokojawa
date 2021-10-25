package com.test.tokoko.service;

import com.test.tokoko.common.payload.BaseResponse;
import com.test.tokoko.module.order.payload.OrderRequest;
import com.test.tokoko.module.product.payload.request.ProductRequest;

public interface OrderService {
    BaseResponse inputOrder(OrderRequest orderRequest);

    BaseResponse updateOrder(OrderRequest orderRequest, Long id);

    BaseResponse cancelOrder(Long id);
}
