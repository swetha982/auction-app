package com.bidding.vendorapp.service.impl;

import com.bidding.commons.exception.DuplicateException;
import com.bidding.vendorapp.dto.VendorDto;
import com.bidding.vendorapp.model.Vendor;
import com.bidding.vendorapp.dao.VendorRepository;
import com.bidding.vendorapp.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    VendorRepository vendorRepository;

    @Override
    @Transactional
    public String signUp(VendorDto vendor) {
        if(!validateVendor(vendor.getEmail())){

           Vendor v=vendorRepository.save(new Vendor(vendor.getName(), vendor.getEmail(), vendor.getPassword()));
           return v!=null?"Vendor Created Successfully":"Vendor Creation Failed";
        }

         throw new DuplicateException("Vendor Already exists with given email: "+ vendor.getEmail());


    }

    public boolean validateVendor(String emailId){
        Optional<Vendor> vendor = vendorRepository.findByEmail(emailId);
        return vendor.isPresent();
    }

    public Vendor findVendor(String emailId){
        return vendorRepository.findByEmail(emailId).get();
    }
}
