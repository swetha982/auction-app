package com.bidding.vendorapp;


import com.bidding.commons.exception.DuplicateException;
import com.bidding.vendorapp.dao.VendorRepository;
import com.bidding.vendorapp.dto.VendorDto;
import com.bidding.vendorapp.model.Vendor;
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
public class VendorServiceTest {

    @Mock
    VendorRepository vendorRepository;

    @InjectMocks
    VendorServiceImpl vendorService;



    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void validateVendor(){
        Mockito.when(vendorRepository.findByEmail("s@gmail.com")).thenReturn(Optional.of(new Vendor("Swetha","s@gmail.com","123")));
      assertEquals(true,vendorService.validateVendor("s@gmail.com"));
    }

    private Optional<Vendor> getVendor(String email){
        if(email.equalsIgnoreCase("s@gmail.com")){
            return Optional.of(new Vendor("Swetha",email,"123"));
        }
        return Optional.empty();
    }
    @Test
    public void create(){
        Mockito.when(vendorRepository.findByEmail("a@gmail.com")).thenReturn(Optional.empty());
        Mockito.when(vendorRepository.save(Mockito.any())).thenReturn(new Vendor("Swetha","a@gmail.com","123"));
        assertEquals(vendorService.signUp(new VendorDto("Swetha","a@gmail.com","123")),"Vendor Created Successfully");
    }

    @Test
    public void createdDataNotAvailable(){
        Mockito.when(vendorRepository.findByEmail("a@gmail.com")).thenReturn(Optional.empty());
        Mockito.when(vendorRepository.save(Mockito.any())).thenReturn(null);
        assertEquals(vendorService.signUp(new VendorDto("Swetha","a@gmail.com","123")),"Vendor Creation Failed");
    }

    @Test(expected = DuplicateException.class)
    public void createGivesException(){
        Mockito.when(vendorRepository.findByEmail("a@gmail.com")).thenReturn(Optional.of(new Vendor("Swetha","a@gmail.com","123")));
        vendorService.signUp(new VendorDto("Swetha","a@gmail.com","123"));
    }

    @Test
    public void findVendor(){
        Mockito.when(vendorRepository.findByEmail("s@gmail.com")).thenReturn(Optional.of(new Vendor("Swetha","s@gmail.com","123")));
        assertEquals("s@gmail.com",vendorService.findVendor("s@gmail.com").getEmail());
    }

    @Test(expected = NoSuchElementException.class)
    public void findVendorGivesException(){
        Mockito.when(vendorRepository.findByEmail("s@gmail.com")).thenReturn(Optional.empty());
        vendorService.findVendor("s@gmail.com");
    }

    @Test
    public void validateVendorGivesException(){
        Mockito.when(vendorRepository.findByEmail("s@gmail.com")).thenReturn(Optional.empty());
        assertFalse(vendorService.validateVendor("s@gmail.com"));
    }
}
