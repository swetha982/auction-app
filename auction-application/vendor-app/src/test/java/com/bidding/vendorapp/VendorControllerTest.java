package com.bidding.vendorapp;

import com.bidding.vendorapp.controller.VendorController;
import com.bidding.vendorapp.dto.VendorDto;
import com.bidding.vendorapp.service.impl.VendorServiceImpl;
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
public class VendorControllerTest {

    @Mock
    VendorServiceImpl vendorService;

    @InjectMocks
    VendorController vendorController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createVendor(){
        Mockito.when(vendorService.signUp(Mockito.any())).thenReturn("Vendor Created Successfully");
        assertEquals("Vendor Created Successfully",vendorController.createVendor(new VendorDto("Swetha","s@gmail.com","123")).getData());
    }
}
