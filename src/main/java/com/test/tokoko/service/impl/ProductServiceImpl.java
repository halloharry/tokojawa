package com.test.tokoko.service.impl;

import com.test.tokoko.common.payload.BaseResponse;
import com.test.tokoko.common.payload.CommonMessage;
import com.test.tokoko.model.Product;
import com.test.tokoko.module.product.payload.request.ProductRequest;
import com.test.tokoko.repository.ProductRepository;
import com.test.tokoko.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public BaseResponse addProduct(ProductRequest productRequest) {
        try {
            Product product = new Product();
            product.setIsDeleted(0);
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setAmount(productRequest.getAmount());
            productRepository.save(product);
            return new BaseResponse(CommonMessage.SAVED, product);
        } catch (Exception e) {
            return new BaseResponse(CommonMessage.NOT_SAVED);
        }
    }

    @Override
    public BaseResponse getAllProduct() {
        try {
            List<Product> products = productRepository.findByIsDeleted(0);
            return new BaseResponse(CommonMessage.FOUND, products);
        } catch (Exception e) {
            return new BaseResponse(CommonMessage.NOT_FOUND);
        }
    }

    @Override
    public BaseResponse getProductByName(String name) {
        try {
            List<Product> product = productRepository.findByName(name);
            return new BaseResponse(CommonMessage.FOUND, product);

        } catch (Exception e) {
            return new BaseResponse(CommonMessage.NOT_FOUND);

        }
    }

    @Override
    public BaseResponse updateProduct(ProductRequest productRequest, Long id) {
        try {
            Product product = productRepository.findByIdAndIsDeleted(id, 0);
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setAmount(productRequest.getAmount());
            product.setIsDeleted(productRequest.getIsDeleted());

            productRepository.save(product);
            return new BaseResponse(CommonMessage.UPDATED, product);

        } catch (Exception e) {
            return new BaseResponse(CommonMessage.NOT_UPDATED);

        }
    }

    @Override
    public BaseResponse deleteProduct(Long id) {
        try {
            Product product = productRepository.findByIdAndIsDeleted(id, 0);
            product.setIsDeleted(1);

            productRepository.save(product);
            return new BaseResponse(CommonMessage.DELETED, null);
        } catch (Exception e) {
            return new BaseResponse(CommonMessage.NOT_DELETED);
        }
    }


}
