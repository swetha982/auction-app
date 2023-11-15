package com.bidding.vendorapp.controller;

import com.bidding.vendorapp.dto.VendorDto;
import com.bidding.vendorapp.service.VendorService;
import com.mindstix.web.rest.baseline.common.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class VendorController {

    @Autowired
    VendorService vendorService;

    @PostMapping("/create/vendor")
    public ApiResponse<String> createVendor(@RequestBody VendorDto vendor) {
        String response = vendorService.signUp(vendor);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        log.info("User Created Successfully");
        return apiResponse;
    }

}
