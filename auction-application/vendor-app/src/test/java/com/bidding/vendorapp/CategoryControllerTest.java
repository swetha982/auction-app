package com.bidding.vendorapp;

import com.bidding.vendorapp.controller.CategoryController;
import com.bidding.vendorapp.service.impl.CategoryServiceImpl;
import com.mindstix.web.rest.baseline.common.model.ApiResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {

    @Mock
    CategoryServiceImpl categoryService;

    @InjectMocks
    CategoryController categoryController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create(){
        Mockito.when(categoryService.create(Mockito.any(),Mockito.any())).thenReturn("Category Created Successfully");
        assertEquals(categoryController.createCategory("Kitchen","s@gmail.com").getData(),"Category Created Successfully");
    }


}
