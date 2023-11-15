package com.bidding.biddingapp;

import com.bidding.biddingapp.controller.AuctionController;
import com.bidding.biddingapp.model.Auction;
import com.bidding.biddingapp.service.AuctionService;
import com.bidding.commons.dto.AuctionDto;
import com.bidding.commons.dto.BiddingRequest;
import com.bidding.commons.dto.ProductResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AuctionControllerTest {

    @InjectMocks
    AuctionController auctionController;

    @Mock
    AuctionService auctionService;

    @Test
    public void addAuctionTest(){
        Mockito.when(auctionService.createAuction(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn("Auction created successfully");
        assertEquals(auctionController.addAuction("Tawa",new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00).getData(), "Auction created successfully");
    }

    @Test
   public void updateAuctiontest(){
        Mockito.when(auctionService.updateAuction(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn("Updated Successfully");
        assertEquals(auctionController.updateAuction(1,1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00).getData(),"Updated Successfully");

   }

   @Test
   public void deleteAuctionTest(){
        Mockito.when(auctionService.deleteAuction(Mockito.anyInt())).thenReturn("Deletion successful");
        assertEquals(auctionController.deleteAuction(1).getData(),"Deletion successful");
   }

   @Test
   public void activeTest(){
        List<Auction> aList = new ArrayList<>();
        aList.add(new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00));
        Mockito.when(auctionService.getActive()).thenReturn(aList);
        assertNotNull(auctionController.activeAuctions().getData());

   }

   @Test
   public void activeTestBycategory(){
       List<AuctionDto> aList = new ArrayList<>();
       ProductResponseDto p = new ProductResponseDto(1,"Tawa","tawa","c://a","kitchen","Swetha");
       aList.add(new AuctionDto(1,p,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00));
//       Mockito.when(auctionService.getActiveByCategory(Mockito.any())).thenReturn(aList);
       assertNotNull(auctionController.activeAuctions().getData());
   }

   @Test
   public void createBidTest(){
        Mockito.when(auctionService.createBid(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn("Bid Created Successfully");
        assertEquals(auctionController.createBid(new BiddingRequest(1,1,1000.00)).getData(),"Bid Created Successfully");
   }
}
