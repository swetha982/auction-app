package com.bidding.vendorapp;

import com.bidding.commons.exception.DuplicateException;
import com.bidding.commons.exception.NoDataFoundException;
import com.bidding.vendorapp.dao.ProductRepository;
import com.bidding.vendorapp.dto.ProductDto;
import com.bidding.vendorapp.model.Category;
import com.bidding.vendorapp.model.Product;
import com.bidding.vendorapp.service.impl.CategoryServiceImpl;
import com.bidding.vendorapp.service.impl.ProductServieImpl;
import com.bidding.vendorapp.service.impl.VendorServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    ProductServieImpl productService;

    @Mock
    VendorServiceImpl vendorService;

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryServiceImpl categoryService;

    @Test
    public void validate(){
        Mockito.when(productRepository.checkForProduct(Mockito.any())).thenReturn(Optional.of(1));
        assertTrue(productService.validate("Stove"));
    }

    @Test
    public void validationFail(){
        Mockito.when(productRepository.checkForProduct(Mockito.any())).thenReturn(Optional.empty());
        assertFalse(productService.validate("Stove"));
    }

    @Test
    public void create(){
        Mockito.when(vendorService.validateVendor(Mockito.any())).thenReturn(true);
        Mockito.when(categoryService.validate(Mockito.any())).thenReturn(true);
        Mockito.when(productRepository.checkForProduct(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(new Product("Stove","stove","url"));
        assertEquals(productService.create(new ProductDto("Stove","stove","url","kitchen","s@gmail.com")),"Product Created Successfully");
    }

    @Test(expected = NoDataFoundException.class)
    public void createWithVendorValidationFailed(){
        Mockito.when(vendorService.validateVendor(Mockito.any())).thenReturn(false);
        productService.create(new ProductDto("Stove","stove","url","kitchen","s@gmail.com"));
    }

    @Test(expected = NoDataFoundException.class)
    public void createWithCategoryValidationFailed(){
        Mockito.when(vendorService.validateVendor(Mockito.any())).thenReturn(true);
        Mockito.when(categoryService.validate(Mockito.any())).thenReturn(false);
        productService.create(new ProductDto("Stove","stove","url","kitchen","s@gmail.com"));
    }

    @Test(expected = DuplicateException.class)
    public void createWithProductValidationFailed(){
        //Mockito.when(vendorService.validateVendor(Mockito.any())).thenReturn(true);
        //Mockito.when(categoryService.validate(Mockito.any())).thenReturn(true);
        Mockito.when(productRepository.checkForProduct(Mockito.any())).thenReturn(Optional.of(1));
        productService.create(new ProductDto("Stove","stove","url","kitchen","s@gmail.com"));
    }

    @Test
    public void createWithProductCreationFailed(){
        Mockito.when(vendorService.validateVendor(Mockito.any())).thenReturn(true);
        Mockito.when(categoryService.validate(Mockito.any())).thenReturn(true);
        Mockito.when(productRepository.checkForProduct(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(null);
        assertEquals(productService.create(new ProductDto("Stove","stove","url","kitchen","s@gmail.com")),"Product creation Failed");
    }

    @Test
    public void showProducts(){
      Mockito.when(productRepository.findAll()).thenReturn(getproductList());
      assertNotNull(productService.showProducts());
    }

    @Test(expected = NoDataFoundException.class)
    public void showProductsNotAvailable(){
        Mockito.when(productRepository.findAll()).thenReturn(new ArrayList<>());
        productService.showProducts();
    }

    @Test
    public void showByCategoryTest(){
        Mockito.when(categoryService.validate(Mockito.any())).thenReturn(true);
        Mockito.when(categoryService.findCategory(Mockito.any())).thenReturn(new Category("Kitchen"));
        Mockito.when(productRepository.findByCategory(Mockito.anyInt())).thenReturn(getproductList());
        assertNotNull(productService.showByCategory("Kitchen"));

    }

    @Test(expected = NoDataFoundException.class)
    public void productsNotAvailableForCategoryTest(){
        Mockito.when(categoryService.validate(Mockito.any())).thenReturn(true);
        Mockito.when(categoryService.findCategory(Mockito.any())).thenReturn(new Category("Kitchen"));
        Mockito.when(productRepository.findByCategory(Mockito.anyInt())).thenReturn(new ArrayList<>());
        productService.showByCategory("Kitchen");

    }

    @Test(expected = NoDataFoundException.class)
    public void showByCategoryValidationFailed(){
        Mockito.when(categoryService.validate(Mockito.any())).thenReturn(false);
        productService.showByCategory("Kitchen");

    }

  /*  @Test
    public void convertTest(){
        List<ProductResponseDto> productResponses = productService.convertProductToResponse(getproductList());
        System.out.println(productResponses);
        //assertEquals(productResponses.get(0).getName(),getproductList().get(0).getName());
    }

    @Test
    public void getProductTest(){

    }*/

    private List<Product> getproductList(){
        List<Product> productList = new ArrayList<>();
        Product p1=new Product("Stove","stove","url");
        Product p2=new Product("Tawa","tawa","url");
        productList.add(p1);
        productList.add(p2);
        return productList;
    }
}
