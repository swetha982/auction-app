package com.bidding.userapp.service;

import dto.UserDto;

public interface UserService {

    public String signUp(UserDto user);

    public boolean login(String emailId, String password);

    String createBid(String username, Integer auctionId, Double amount);

    String showEmail(Integer userId);
}
