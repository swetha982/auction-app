package com.bidding.biddingapp.controller;

import com.bidding.commons.dto.AuctionDto;
import com.bidding.biddingapp.model.Auction;
import com.bidding.biddingapp.service.AuctionService;
import com.bidding.commons.dto.BiddingRequest;
import com.mindstix.web.rest.baseline.common.model.ApiResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    AuctionService auctionService;

    @PostMapping("/create")
    public ApiResponse<String> addAuction(@RequestParam String productName, @RequestParam Date startTime,
                                          @RequestParam Date endTime, @RequestParam Double basePrice){

        String result = auctionService.createAuction(productName,startTime,endTime,basePrice);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setData(result);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        log.info("Auction Creation");
        return apiResponse;
    }

    @PutMapping("/update")
    public ApiResponse<String> updateAuction(@RequestParam Integer aucId, @RequestParam Integer productId, @RequestParam Date startTime,
                                             @RequestParam Date endTime, @RequestParam Double basePrice){
        String result = auctionService.updateAuction(aucId,productId,startTime,endTime,basePrice);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setData(result);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        log.info("Auction Updation");
        return apiResponse;
    }

    @PostMapping("/delete/{id}")
    public ApiResponse<String> deleteAuction(@PathVariable Integer id){
        String result = auctionService.deleteAuction(id);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setData(result);
        apiResponse.setStatus(HttpStatus.NO_CONTENT.value());
        log.info("Auction Deletion");
        return apiResponse;
    }

    @GetMapping("/active")
    public ApiResponse<List<AuctionDto>> activeAuctions(){
        List<Auction> a = auctionService.getActive();
        List<AuctionDto> auctionDtoList = auctionService.convertAuctionToResponse(a);
        ApiResponse<List<AuctionDto>> apiResponse = new ApiResponse<>();
        apiResponse.setData(auctionDtoList);
        apiResponse.setStatus(HttpStatus.OK.value());
        log.info("Active Auctions Retrieval");
        return apiResponse;
    }

    @GetMapping("/active/{categoryName}")
    public ApiResponse<List<AuctionDto>> activeAuctionsByCategory(@PathVariable  String categoryName){
        List<AuctionDto> result = auctionService.getActiveByCategory(categoryName);
        ApiResponse<List<AuctionDto>> apiResponse = new ApiResponse<>();
        apiResponse.setData(result);
        apiResponse.setStatus(HttpStatus.OK.value());
        log.info("Active Auctions Retrieval");
        return apiResponse;
    }
    @PostMapping("/bid")
    public ApiResponse<String> createBid(@RequestBody BiddingRequest request){
        String response = auctionService.createBid(request.getUserId(),request.getAuctionId(),request.getAmount());
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        log.info("Bid Creation");
        return apiResponse;

    }

}
