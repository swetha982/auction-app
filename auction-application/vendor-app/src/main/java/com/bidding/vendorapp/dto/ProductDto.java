package com.bidding.vendorapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {

    private String name;
    private String description;
    private String imageUrl;
    private String category;
    private String vendorEmail;
}
