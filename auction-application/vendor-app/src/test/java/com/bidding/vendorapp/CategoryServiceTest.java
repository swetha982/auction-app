package com.bidding.vendorapp;

import com.bidding.commons.exception.DuplicateException;
import com.bidding.commons.exception.NoDataFoundException;
import com.bidding.vendorapp.dao.CategoryRepository;
import com.bidding.vendorapp.dao.VendorRepository;
import com.bidding.vendorapp.model.Category;
import com.bidding.vendorapp.model.Vendor;
import com.bidding.vendorapp.service.impl.CategoryServiceImpl;
import com.bidding.vendorapp.service.impl.VendorServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    VendorRepository vendorRepository;

    @Mock
    VendorServiceImpl vendorService;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validate(){
        Mockito.when(categoryRepository.findByName(Mockito.any())).thenReturn(Optional.of(new Category("Kitchen")));
        assertTrue(categoryService.validate("Kitchen"));
    }

    @Test
    public void validationFailed(){
        Mockito.when(categoryRepository.findByName(Mockito.any())).thenReturn(Optional.empty());
        assertFalse(categoryService.validate("Kitchen"));
    }

    @Test
    public void find(){
        Mockito.when(categoryRepository.findCategory(Mockito.any())).thenReturn(Optional.of(new Category("Kitchen")));
        assertEquals(categoryService.findCategory("Kitchen"),new Category("Kitchen"));
    }

    @Test(expected = NoSuchElementException.class)
    public void findGivesNoValuePresent(){
        Mockito.when(categoryRepository.findCategory(Mockito.any())).thenReturn(Optional.empty());
        assertEquals(categoryService.findCategory("Kitchen"),new Category("Kitchen"));
    }

    @Test
    public void create(){
        Mockito.when(categoryRepository.findByName(Mockito.any())).thenReturn(Optional.empty());
        //Mockito.when(vendorRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(new Vendor("Swetha","s@gmail.com","123")));
        Mockito.when(vendorService.validateVendor(Mockito.any())).thenReturn(true);
        Mockito.when(vendorService.findVendor(Mockito.any())).thenReturn(new Vendor("Swetha","s@gmail.com","123"));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(new Category("Kitchen"));
        assertEquals(categoryService.create("Kitchen","s@gmail.com"),"Category created successfully");
    }
    @Test
    public void createNotCreated(){
        Mockito.when(categoryRepository.findByName(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(vendorService.validateVendor(Mockito.any())).thenReturn(true);
        Mockito.when(vendorService.findVendor(Mockito.any())).thenReturn(new Vendor("Swetha","s@gmail.com","123"));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(null);
        assertEquals(categoryService.create("Kitchen","s@gmail.com"),"Category creation failed");
    }

    @Test(expected = NoDataFoundException.class)
    public void createGivesException(){
        //Mockito.when(vendorRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());
        categoryService.create("Kitchen","s@gmail.com");
    }

    @Test(expected = DuplicateException.class)
    public void createCategoryDuplicateException(){
        Mockito.when(categoryRepository.findByName(Mockito.any())).thenReturn(Optional.of(new Category("Kitchen")));
        categoryService.create("Kitchen","s@gmail.com");
    }

}
