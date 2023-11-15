package com.bidding.userapp.service.impl;

import com.bidding.commons.dto.BiddingRequest;
import com.bidding.commons.exception.DuplicateException;
import com.bidding.commons.exception.NoDataFoundException;
import com.bidding.userapp.dao.UserRepository;
import com.bidding.userapp.model.User;
import com.bidding.userapp.service.UserService;
import dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    RestTemplate restTemplate;

    private String biddingurl ="http://localhost:8082/auction";

    @Override
    public String signUp(UserDto user) {
        if(!validateUser(user.getEmail())){
          User newUser = new User(user.getName(),user.getEmail(),user.getPassword());
            return userRepo.save(newUser) != null ? "Successfully signed up": "Sign Up Failed. Please Try Again";
        }

          throw new DuplicateException("User Already Exists. Please Login.");
    }

    public boolean login(String emailId, String password){
        Optional<User> user = userRepo.findByEmail(emailId);
        return user.isPresent();
    }

    @Override
    public String createBid(String username, Integer auctionId, Double amount) {
        Optional<User> u =userRepo.findByEmail(username);
        log.info("In Create Bid");
        if(u.isPresent()){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            BiddingRequest biddingRequest = new BiddingRequest(u.get().getUserId(),auctionId,amount);
            HttpEntity<BiddingRequest> entity = new HttpEntity<>(biddingRequest,headers);
            String url = biddingurl+"/bid";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,entity,String.class);
            System.out.println("Response:"+response);
            String result = response.getBody();
            return result;
        }
       throw new NoDataFoundException("User Not Exists");
    }

    @Override
    public String showEmail(Integer userId) {
        Optional<String> email =userRepo.findEmail(userId);
        if(email.isPresent()){
            return email.get();
        }
       throw new NoDataFoundException("No user Found");
    }

    private boolean validateUser(String emailId){
        Optional<User> user = userRepo.findByEmail(emailId);
        return user.isPresent();
    }
}
