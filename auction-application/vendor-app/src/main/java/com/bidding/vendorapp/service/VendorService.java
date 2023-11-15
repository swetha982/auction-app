package com.bidding.vendorapp.service;

import com.bidding.vendorapp.dto.VendorDto;
import com.bidding.vendorapp.model.Vendor;

public interface VendorService {

    public String signUp(VendorDto vendor);

    public boolean validateVendor(String emailId);

    public Vendor findVendor(String emailId);
}
