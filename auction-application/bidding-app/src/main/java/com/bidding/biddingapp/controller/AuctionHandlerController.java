package com.bidding.biddingapp.controller;

import com.bidding.biddingapp.model.Auction;
import com.bidding.biddingapp.service.AuctionService;

import com.bidding.commons.dto.AuctionDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class AuctionHandlerController {

    @Autowired
    AuctionService auctionService;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    ObjectMapper objectMapper;

    @Scheduled(fixedRate = 10000)
    public void sendAuctionsToUsers() throws JsonProcessingException {
        List<Auction> a = auctionService.getActive();
        List<AuctionDto> auctionDtoList = auctionService.convertAuctionToResponse(a);

            String object = objectMapper.writeValueAsString(auctionDtoList);
            Map<String, Object> map = new HashMap<>();
            map.put("type", "normal");
            System.out.println("Obj:" + object);
            //template.convertAndSend("/all",object);
            template.convertAndSend("/all", object, map);

    }

    @Scheduled(fixedRate = 10000)
    public void endAuction(){
        List<Auction> aucList = auctionService.getAllAuctions();
        Map<String,Object> map = new HashMap<>();
        map.put("type","win");
        for(Auction a:aucList){
            if(a.getEndTime().getTime() == System.currentTimeMillis() || a.getEndTime().getTime() < System.currentTimeMillis()){
                log.info("Declaring winner..............");
                String res="You have won this auction: "+a.getAuctionId();
                Set<String> winnerEmails= auctionService.selectWinner(a);
                log.info("After.........................");
                if(!winnerEmails.isEmpty()) {
                    for (String email : winnerEmails) {
                        log.info("Email............"+email);
                        template.convertAndSendToUser(email, "/win", res, map);
                    }
                }
                auctionService.deleteAuction(a.getAuctionId());
                log.info("Deleting.........................");
            }
        }

    }


}
