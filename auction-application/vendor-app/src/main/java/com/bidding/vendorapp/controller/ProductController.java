package com.bidding.vendorapp.controller;

import com.bidding.vendorapp.dto.ProductDto;
import com.bidding.vendorapp.service.ProductService;
import com.bidding.vendorapp.model.Product;
import com.mindstix.web.rest.baseline.common.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.bidding.commons.dto.ProductResponseDto;

import java.util.List;

@RestController
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;


    @PostMapping("/create/product")
    public ApiResponse<String> createProduct(@RequestBody ProductDto product) {
        String response = productService.create(product);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        log.info("Product Created Successfully");
        return apiResponse;
    }

    @GetMapping("/products/{categoryName}")
    public ApiResponse<List<ProductResponseDto>> showProductsByCategory(@PathVariable String categoryName) {
        List<Product> productList = productService.showByCategory(categoryName);
        List<ProductResponseDto> response = productService.convertProductToResponse(productList);
        ApiResponse<List<ProductResponseDto>> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setStatus(HttpStatus.OK.value());
        log.info("Product Details Retrieved Successfully");
        return apiResponse;
    }

    @GetMapping("/products")
    public ApiResponse<List<ProductResponseDto>> showProducts() {
        List<Product> productList = productService.showProducts();
        List<ProductResponseDto> response = productService.convertProductToResponse(productList);
        ApiResponse<List<ProductResponseDto>> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setStatus(HttpStatus.OK.value());
        log.info("Product Details Retrieved Successfully");
        return apiResponse;
    }

    @GetMapping("/product/{productName}")
    public ApiResponse<ProductResponseDto> showProductDetails(@PathVariable String productName) {
        ProductResponseDto productResponseDto = productService.getProduct(productName,null);
        ApiResponse<ProductResponseDto> apiResponse = new ApiResponse<>();
        apiResponse.setData(productResponseDto);
        apiResponse.setStatus(HttpStatus.OK.value());
        log.info("Product Details Retrieved Successfully");
        return apiResponse;
    }

    @GetMapping("/show-products/{productId}")
    public ApiResponse<ProductResponseDto> showProductDetailsById(@PathVariable Integer productId) {
        ProductResponseDto productResponseDto = productService.getProduct(null,productId);
        ApiResponse<ProductResponseDto> apiResponse = new ApiResponse<>();
        apiResponse.setData(productResponseDto);
        apiResponse.setStatus(HttpStatus.OK.value());
        log.info("Product Details Retrieved Successfully");
        return apiResponse;
    }
}
