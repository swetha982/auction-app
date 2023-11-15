package com.bidding.vendorapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VendorDto {
    private String name;
    private String email;
    private String password;
}
