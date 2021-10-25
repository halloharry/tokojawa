package com.test.tokoko.service;

import com.test.tokoko.common.payload.BaseResponse;
import com.test.tokoko.module.product.payload.request.ProductRequest;

public interface ProductService {

    BaseResponse addProduct(ProductRequest productRequest);

    BaseResponse getAllProduct();

    BaseResponse getProductByName(String name);

    BaseResponse updateProduct(ProductRequest productRequest, Long id);

    BaseResponse deleteProduct(Long id);
}
