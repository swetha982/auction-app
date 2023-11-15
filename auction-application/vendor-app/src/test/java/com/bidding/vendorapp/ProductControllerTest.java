package com.bidding.vendorapp;

import com.bidding.commons.dto.ProductResponseDto;
import com.bidding.vendorapp.controller.ProductController;
import com.bidding.vendorapp.dto.ProductDto;
import com.bidding.vendorapp.model.Product;
import com.bidding.vendorapp.service.impl.ProductServieImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductServieImpl productService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTest(){
        Mockito.when(productService.create(Mockito.any())).thenReturn("Product Created Successfully");
        assertEquals(productController.createProduct(new ProductDto("Stove","stove","url","kitchen","s@gmail.com")).getData(),"Product Created Successfully");
    }
    @Test
    public void showProductsTest(){
        Mockito.when(productService.showProducts()).thenReturn(getproductList());
        assertNotNull(productController.showProducts());
    }

    @Test
    public void productListByCategoryTest(){
        Mockito.when(productService.showByCategory(Mockito.any())).thenReturn(getproductList());
        Mockito.when(productService.convertProductToResponse(Mockito.any())).thenReturn(getproductResponseList());
        assertNotNull(productController.showProductsByCategory("kitchen").getData());

    }

    @Test
    public void productDtlsById(){
        Mockito.when(productService.getProduct(Mockito.any(),Mockito.any())).thenReturn(new ProductResponseDto(1,"Stove","stove","url","kitchen","s@gmail.com"));
        assertNotNull(productController.showProductDetailsById(1).getData());
    }

    @Test
    public void productDtlsByName(){
        Mockito.when(productService.getProduct(Mockito.any(),Mockito.any())).thenReturn(new ProductResponseDto(1,"Stove","stove","url","kitchen","s@gmail.com"));
        assertNotNull(productController.showProductDetails("Stove").getData());
    }

    private List<Product> getproductList(){
        List<Product> productList = new ArrayList<>();
        Product p1=new Product("Stove","stove","url");
        Product p2=new Product("Tawa","tawa","url");
        productList.add(p1);
        productList.add(p2);
        return productList;
    }

    private List<ProductResponseDto> getproductResponseList(){
        List<ProductResponseDto> productList = new ArrayList<>();
        ProductResponseDto p1=new ProductResponseDto(1,"Stove","stove","url","kitchen","s@gmail.com");
        ProductResponseDto p2=new ProductResponseDto(2,"Tawa","tawa","url","kitchen","s@gmail.com");
        productList.add(p1);
        productList.add(p2);
        return productList;
    }
}
