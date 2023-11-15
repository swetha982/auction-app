package com.bidding.vendorapp.service;

import com.bidding.vendorapp.model.Category;

public interface CategoryService {

    public String create(String name, String vendorEmail);

    public boolean validate(String name);

    public Category findCategory(String name);
}
