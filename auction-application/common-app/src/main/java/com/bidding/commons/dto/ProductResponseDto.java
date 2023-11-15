package com.bidding.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    @com.fasterxml.jackson.annotation.JsonProperty("id")
    private int id;
    @com.fasterxml.jackson.annotation.JsonProperty("name")
    private String name;
    @com.fasterxml.jackson.annotation.JsonProperty("description")
    private String description;
    @com.fasterxml.jackson.annotation.JsonProperty("imageUrl")
    private String imageUrl;
    @com.fasterxml.jackson.annotation.JsonProperty("category")
    private String category;
    @com.fasterxml.jackson.annotation.JsonProperty("vendorName")
    private String vendorName;
}
