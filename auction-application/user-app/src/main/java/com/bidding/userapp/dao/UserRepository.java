package com.bidding.userapp.dao;

import com.bidding.userapp.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String emailId);

    @Query(value = "select email from user where user_id = :userId",nativeQuery = true)
    Optional<String> findEmail(Integer userId);
}
