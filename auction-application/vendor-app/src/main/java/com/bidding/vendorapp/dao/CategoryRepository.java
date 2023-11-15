package com.bidding.vendorapp.dao;

import com.bidding.vendorapp.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Integer> {

    Optional<Category> findByName(String name);

    @Query(value = "select cat.* from categories cat where upper(name)= :name",nativeQuery = true)
    Optional<Category> findCategory(String name);
}
