package com.bidding.userapp.controller;

import com.bidding.userapp.service.UserService;
import com.mindstix.web.rest.baseline.common.model.ApiResponse;
import dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user-app")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signUp")
    public ApiResponse<String> registerUser(@RequestBody UserDto user){
        String response = userService.signUp(user);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        log.info("User Created Successfully");
        return apiResponse;
    }

    @GetMapping("/login")
    public ApiResponse<String> login(@RequestParam String userName, @RequestParam String password){
        ApiResponse<String> apiResponse = new ApiResponse<>();
       if(userService.login(userName,password)){
           apiResponse.setData("Successfully logged in");
           apiResponse.setStatus(HttpStatus.OK.value());
           log.info("User Successfully logged in");
       }else{
           apiResponse.setData("Email or Password wrong");
           apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
           log.info("Login Failed");
       }
       return apiResponse;
    }
    @PostMapping("/bid")
   public ApiResponse<String> generateBid(@RequestParam String username, @RequestParam Integer auctionId, @RequestParam Double amount){
        log.info("Bidding............");
        String response = userService.createBid(username,auctionId,amount);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        log.info("Bid Creation");
        return apiResponse;

   }

   @GetMapping("/id/{userId}")
   public ApiResponse<String> getUserMailId(@PathVariable Integer userId){
       String response = userService.showEmail(userId);
       ApiResponse<String> apiResponse = new ApiResponse<>();
       apiResponse.setData(response);
       apiResponse.setStatus(HttpStatus.OK.value());
       log.info("Sending User Maid Id Details");
       return apiResponse;
   }






}
