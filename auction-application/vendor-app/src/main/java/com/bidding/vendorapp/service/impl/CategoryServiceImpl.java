package com.bidding.vendorapp.service.impl;

import com.bidding.commons.exception.DuplicateException;
import com.bidding.commons.exception.NoDataFoundException;
import com.bidding.vendorapp.dao.CategoryRepository;
import com.bidding.vendorapp.model.Category;
import com.bidding.vendorapp.model.Vendor;
import com.bidding.vendorapp.service.CategoryService;
import com.bidding.vendorapp.service.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorService vendorService;

    @Override
    @Transactional
    public String create(String name, String vendorEmail) {
        log.info("In service category"+ name);
        if(!validate(name)){
            if(vendorService.validateVendor(vendorEmail)){
                Vendor v = vendorService.findVendor(vendorEmail);
                Category c = new Category(name,v);

                return (categoryRepository.save(c))!=null?"Category created successfully":"Category creation failed";
            }else
                throw new NoDataFoundException("Vendor doesn't exist");
        }
        else
            throw new DuplicateException("Category Already exists");
    }

    public boolean validate(String name){
        Optional<Category> category = categoryRepository.findByName(name);
        return  category.isPresent();
    }

    public Category findCategory(String name){
        String nameUpper=name.toUpperCase();
        return categoryRepository.findCategory(nameUpper).get();
    }
}
