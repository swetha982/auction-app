package com.bidding.biddingapp.service.impl;

import com.bidding.biddingapp.dao.AuctionRepository;
import com.bidding.biddingapp.dao.BidRepository;
import com.bidding.biddingapp.dao.WinnerRepository;
import com.bidding.biddingapp.model.Winner;
import com.bidding.biddingapp.util.AuctionServiceUtil;
import com.bidding.commons.dto.AuctionDto;
import com.bidding.biddingapp.model.Auction;
import com.bidding.biddingapp.model.Bid;
import com.bidding.biddingapp.service.AuctionService;

import com.bidding.commons.dto.ProductResponseDto;
import com.bidding.commons.exception.DuplicateException;
import com.bidding.commons.exception.NoDataFoundException;
import com.google.protobuf.Api;
import com.mindstix.web.rest.baseline.common.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class AuctionServiceImpl implements AuctionService {


    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    BidRepository bidRepository;

    @Autowired
    WinnerRepository winnerRepository;

    @Autowired
    AuctionServiceUtil auctionServiceUtil;

    @Override
    @Transactional
    public String createAuction(String productName, Date startTime, Date endTime, Double basePrice) {
       if(basePrice > 0) {
           ProductResponseDto p = auctionServiceUtil.getProductDtlsByName(productName);
           if (p != null) {
               if (!auctionRepository.findByProduct(p.getId()).isPresent()) {
                   Auction auction = new Auction(p.getId(), startTime, endTime, basePrice);
                   auction.setBids(new ArrayList<>());

                   return (auctionRepository.save(auction) != null) ? "Auction created successfully" : "Auction Creation failed";
               }
               throw new DuplicateException("An auction is available for the given product->" + p.getId());
           }
           throw new NoDataFoundException("Given Product is not available");
       }
       throw new RuntimeException("Base price should be > 0");
    }


    @Transactional
    @Override
    public String updateAuction(Integer aucId, Integer productId, Date startTime, Date endTime, Double basePrice) {
        Optional<Auction> a =auctionRepository.findById(aucId);
        if(a.isPresent()){
            a.get().setProductId(productId);
            a.get().setStartTime(startTime);
            a.get().setEndTime(endTime);
            a.get().setBasePrice(basePrice);
            return auctionRepository.save(a.get()) != null?"Updated Successfully":"Auction updation failed";
        }
        throw new NoDataFoundException("Auction not available to update");
    }

    @Override
    @Transactional
    public String deleteAuction(Integer id) {
        System.out.println("Inside delete");
        Optional<Auction> a =auctionRepository.findById(id);
        if(a.isPresent()){
            bidRepository.deleteBYAuctionId(a.get().getAuctionId());
            System.out.println("Inside delete "+a.get());
            auctionRepository.deleteById(id);
            return auctionRepository.findById(id).isPresent()?"Deletion failed":"Deletion successful";
        }
            throw new NoDataFoundException("Auction is not available to delete");

    }

    @Transactional
    @Override
    public Set<String> selectWinner(Auction a){
        Optional<Double> winAmount = Optional.ofNullable(bidRepository.getMaxAmount(a.getAuctionId()));
        Set<String> winnerEmails = new TreeSet<>();
        if(winAmount.isPresent()) {
            Set<Integer> winners = bidRepository.findWinner(winAmount.get(), a.getAuctionId());
            if (!winners.isEmpty()) {
                for (int i : winners) {
                    Winner w = new Winner(a.getProductId(), i, a.getAuctionId(), a.getBasePrice(), winAmount.get());
                    winnerRepository.save(w);
                    String email = auctionServiceUtil.getUserDtls(i);
                    if(email != null){
                        winnerEmails.add(email);
                    }
                }
            }
        }
        return winnerEmails;
    }

    @Override
    public List<Auction> getActive() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            List<Auction> auctions = auctionRepository.findByTime(now);
            return auctions;
        }catch (Exception e){
            log.info(" Error Response from Http call " + e.toString());
        }
        return null;
    }

    public List<AuctionDto> convertAuctionToResponse(List<Auction> auctions){
        List<AuctionDto> auctionDtoList = new ArrayList<>();
        try {
            for (Auction a : auctions) {
                ProductResponseDto p = auctionServiceUtil.getProductDtlsById(a.getProductId());
                AuctionDto auctionDto = new AuctionDto(a.getAuctionId(), p, a.getStartTime(), a.getEndTime(), a.getBasePrice());
                auctionDtoList.add(auctionDto);
            }
            return auctionDtoList;
        }catch (Exception e){
            log.info(" Error Response from Http call " + e.toString());
        }
        return null;
    }

    public List<AuctionDto> convertAuctionToResponseByCategory(List<Auction> auctions){
        List<AuctionDto> auctionDtoList = new ArrayList<>();
        try {
            for (Auction a : auctions) {
                ProductResponseDto p = auctionServiceUtil.getProductDtlsById(a.getProductId());
                AuctionDto auctionDto = new AuctionDto(a.getAuctionId(), p, a.getStartTime(), a.getEndTime(), a.getBasePrice());
                auctionDtoList.add(auctionDto);
            }
            return auctionDtoList;
        }catch (Exception e){
            log.info(" Error Response from Http call " + e.toString());
        }
        return null;
    }


    @Override
    public List<AuctionDto> getActiveByCategory(String categoryName){
        List<AuctionDto> auctionList = new ArrayList<>();
        System.out.println("List Inside:");
        List<ProductResponseDto> productResponseDtoList = auctionServiceUtil.getProductDtlsByCategoty(categoryName);
        System.out.println("List:"+productResponseDtoList);
        for(ProductResponseDto p:productResponseDtoList){
            Optional<Auction> a= auctionRepository.findByProductId(p.getId());
            //System.out.println("Auction:"+a.get());

            if(a.isPresent()){
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                Optional<Integer> activeId=auctionRepository.findActiveByCategory(now,a.get().getAuctionId());
                if(activeId.isPresent()) {
                    AuctionDto auctionDto = new AuctionDto(a.get().getAuctionId(), p, a.get().getStartTime(), a.get().getEndTime(), a.get().getBasePrice());
                    auctionList.add(auctionDto);
                }
            }

        }
        return auctionList;
    }


    @Override
    @Transactional
    public String createBid(Integer userId, Integer auctionId, Double amount){
        Auction a = auctionRepository.findById(auctionId).get();
        if(a!=null){
            if(a.getBasePrice()<amount){
                Bid b = new Bid(amount,userId);
                b.setAuction(a);
                Bid bid =  bidRepository.save(b);
                List<Bid>bids=a.getBids();
                bids.add(bid);
                a.setBids(bids);
                return auctionRepository.save(a)!=null?"Bid Created Successfully":"Bid creation failed";
            }
                throw new RuntimeException("Bid Value is lesser than the base price");
        }else
            throw new NoDataFoundException("Auction Doesnt exists ->"+auctionId);
    }

    @Override
    public List<Auction> getAllAuctions(){
        try {
            List<Auction> a = (List<Auction>) auctionRepository.findAll();
            return a;
        }catch (Exception e){
            log.info(" Error while getting all auctions " + e.toString());
        }
        return null;
    }



   /* public void sendActiveAuctionsToUser(List<AuctionDto> auctionList){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<AuctionDto>> entity = new HttpEntity<>(auctionList,headers);
        String url = userUrl+"/user/active-auctions";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,entity,String.class);
        System.out.println("Response:"+response);
        String result = response.getBody();
       System.out.println(result);

    }*/

}
