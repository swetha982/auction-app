package com.bidding.vendorapp.controller;

import com.bidding.vendorapp.service.CategoryService;
import com.mindstix.web.rest.baseline.common.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create/category")
    public ApiResponse<String> createCategory(@RequestParam String categoryName, @RequestParam String vendorEmail){
        String response = categoryService.create(categoryName,vendorEmail);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        log.info("Category Created Successfully");
        return apiResponse;
    }

}
