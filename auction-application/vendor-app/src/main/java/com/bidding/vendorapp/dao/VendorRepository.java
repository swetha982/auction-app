package com.bidding.vendorapp.dao;

import com.bidding.vendorapp.model.Vendor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VendorRepository extends CrudRepository<Vendor, Integer> {
    Optional<Vendor> findByEmail(String emailId);
}
