package com.test.tokoko.module.product.controller;

import com.test.tokoko.common.payload.BaseResponse;
import com.test.tokoko.module.product.payload.request.ProductRequest;
import com.test.tokoko.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add-product")
    public BaseResponse addProduct(@RequestBody ProductRequest productRequest){
        System.out.println("hasil add : " + productRequest);
        return productService.addProduct(productRequest);
    }

    @GetMapping("/")
    public BaseResponse getAllProduct(){
        return productService.getAllProduct();
    }

    @GetMapping("/{name}")
    public BaseResponse getProductByName(@PathVariable String name){
        System.out.println(name);
        return productService.getProductByName(name);
    }

    @PostMapping("/update-product/{id}")
    public BaseResponse updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest){
        System.out.println(productRequest);
        return productService.updateProduct(productRequest, id);
    }

    @PostMapping("/delete-product/{id}")
    public BaseResponse deleteProduct(@PathVariable("id") Long id){
        return productService.deleteProduct(id);
    }

}
