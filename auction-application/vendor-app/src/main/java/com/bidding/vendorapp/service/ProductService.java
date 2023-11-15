package com.bidding.vendorapp.service;

import com.bidding.vendorapp.dto.ProductDto;
import com.bidding.vendorapp.model.Product;
import com.bidding.commons.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {

    public String create(ProductDto product);

    public List<Product> showByCategory(String categoryName);

    public List<ProductResponseDto> convertProductToResponse(List<Product> products);

    public List<Product> showProducts();

    public ProductResponseDto getProduct(String name, Integer productId);
}
