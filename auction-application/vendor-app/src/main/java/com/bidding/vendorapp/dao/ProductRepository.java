package com.bidding.vendorapp.dao;

import com.bidding.vendorapp.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,Integer> {

    @Query(value = "select prod.* from products prod where cat_id=:catId",nativeQuery = true)
    List<Product> findByCategory(int catId);


    @Query(value = "select product_id from products where upper(name) = :productName", nativeQuery = true)
    Optional<Integer> checkForProduct(String productName);

    @Query(value = "select prod.product_id,prod.name,prod.description,prod.image_url,cat.name as category,v.name as vendorName from categories cat " +
            "inner join products prod ON prod.cat_id = cat.cat_id inner join vendors v ON prod.vendor_id = v.vendor_id  where upper(prod.name)=:productName or prod.product_id=:id",nativeQuery = true)
    Optional<String[]> findProductByName(String productName, Integer id);
}
