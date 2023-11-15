package com.bidding.biddingapp;

import com.bidding.biddingapp.dao.AuctionRepository;
import com.bidding.biddingapp.dao.BidRepository;
import com.bidding.biddingapp.dao.WinnerRepository;
import com.bidding.biddingapp.model.Auction;
import com.bidding.biddingapp.model.Bid;
import com.bidding.biddingapp.model.Winner;
import com.bidding.biddingapp.service.impl.AuctionServiceImpl;
import com.bidding.biddingapp.util.AuctionServiceUtil;
import com.bidding.commons.dto.ProductResponseDto;
import com.bidding.commons.exception.NoDataFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mindstix.web.rest.baseline.common.model.ApiResponse;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;

import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
public class AuctionServiceTest {

    @InjectMocks
    AuctionServiceImpl auctionService;

    @Mock
    AuctionRepository auctionRepository;

    @Mock
    BidRepository bidRepository;

    @Mock
    AuctionServiceUtil auctionServiceUtil;

    @Mock
    WinnerRepository winnerRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTest() throws Exception {

        Mockito.when(auctionServiceUtil.getProductDtlsByName(Mockito.any())).thenReturn(new ProductResponseDto(1,"Tawa","tawa","c://a","kitchen","Swetha"));
         Mockito.when(auctionRepository.findByProduct(Mockito.anyInt())).thenReturn(Optional.empty());
         Mockito.when(auctionRepository.save(Mockito.any())).thenReturn(new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00));
         assertEquals(auctionService.createAuction("Tawa",new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00),"Auction created successfully");
    }

    @Test
    public void createTestFailed(){
        Mockito.when(auctionServiceUtil.getProductDtlsByName(Mockito.any())).thenReturn(new ProductResponseDto(1,"Tawa","tawa","c://a","kitchen","Swetha"));
        Mockito.when(auctionRepository.findByProduct(Mockito.anyInt())).thenReturn(Optional.empty());
        assertEquals(auctionService.createAuction("Tawa",new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00),"Auction Creation failed");

    }

    @Test(expected= NoDataFoundException.class)
    public void createTestFailedDueToNoProduct(){
        Mockito.when(auctionServiceUtil.getProductDtlsByName(Mockito.any())).thenReturn(null);
        assertEquals(auctionService.createAuction("Tawa",new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00),"Auction Creation failed");

    }

    @Test
    public void updateTest(){
        when(auctionRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00)));
        when(auctionRepository.save(Mockito.any())).thenReturn(new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),980.00));
        assertEquals(auctionService.updateAuction(2,1, new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),980.00),"Updated Successfully");
    }

    @Test
    public void updateTestFailed(){
        when(auctionRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00)));
        //when(auctionRepository.save(Mockito.any())).thenReturn(new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),980.00));
        assertEquals(auctionService.updateAuction(2,1, new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),980.00),"Auction updation failed");
    }

    @Test(expected = NoDataFoundException.class)
    public void updateTestGivesException(){
        when(auctionRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        //when(auctionRepository.save(Mockito.any())).thenReturn(new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),980.00));
        assertEquals(auctionService.updateAuction(2,1, new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),980.00),"Auction updation failed");
    }
    @Test(expected = NoDataFoundException.class)
    public void deleteTest(){
        when(auctionRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00)));
//        Mockito.doNothing().when(bidRepository).deleteBYAuctionId(any());
  //      Mockito.doNothing().when(auctionRepository).deleteById(any());
        when(auctionRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertEquals(auctionService.deleteAuction(1),"Deletion successfull");

    }

    @Test
    public void selectWinner(){
        Set<Integer> wins = new TreeSet<>();
        wins.add(1);
 //       when(bidRepository.getMaxAmount(Mockito.anyInt())).thenReturn(1090.90);
        when(bidRepository.findWinner(Mockito.any(),Mockito.any())).thenReturn(wins);
        when(auctionServiceUtil.getUserDtls(Mockito.anyInt())).thenReturn("v@gmail.com");
        when(winnerRepository.save(Mockito.any())).thenReturn(new Winner(1,1,1,1000.00,1090.00));
        Set<String> email =auctionService.selectWinner(new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00));
        assertTrue(email.contains("v@gmail.com"));
    }
    @Test
    public void getAllAuctionsTest(){
        List<Auction> aList = new ArrayList<>();
        aList.add(new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00));
        when(auctionRepository.findAll()).thenReturn(aList);
        assertNotNull(auctionService.getAllAuctions());
    }

    @Test
    public void createBidTest(){
        Auction a=new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00);
        List<Bid> bids = new ArrayList<>();
        bids.add(new Bid(1200.00,1));
        a.setBids(bids);
        when(auctionRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(a));
        when(bidRepository.save(Mockito.any())).thenReturn(new Bid(1200.00,1));
        when(auctionRepository.save(Mockito.any())).thenReturn(new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00));
        assertEquals(auctionService.createBid(1,1,1200.00),"Bid Created Successfully");

    }
    @Test
    public void getActiveTest(){
        Auction a=new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00);
        List<Auction> auctionList = new ArrayList<>();
        auctionList.add(a);
        when(auctionRepository.findByTime(Mockito.any())).thenReturn(auctionList);
        assertEquals(auctionService.getActive().get(0),a);

    }

    @Test
    public void convertAuctionToResponse(){
        when(auctionServiceUtil.getProductDtlsById(Mockito.any())).thenReturn(new ProductResponseDto(1,"Tawa","tawa","c://a","kitchen","Swetha"));
        Auction a=new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00);
        List<Auction> auctionList = new ArrayList<>();
        auctionList.add(a);
        assertNotNull(auctionService.convertAuctionToResponse(auctionList).get(0));
    }

    @Test
    public void getActiveByCategoryTest(){
        ProductResponseDto p = new ProductResponseDto(1,"Tawa","tawa","c://a","kitchen","Swetha");
        List<ProductResponseDto> pList = new ArrayList<>();
        pList.add(p);
        Auction a=new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00);
        when(auctionServiceUtil.getProductDtlsByCategoty(Mockito.any())).thenReturn(pList);
        when(auctionRepository.findByProductId(Mockito.anyInt())).thenReturn(Optional.of(a));
//        when(auctionRepository.findActiveByCategory(Mockito.any(),Mockito.anyInt())).thenReturn(Optional.of(1));
        assertNotNull(auctionService.getActiveByCategory("Kitchen"));

    }
    @Test
    public void getActiveByCategoryTestGivesEmpty(){
        ProductResponseDto p = new ProductResponseDto(1,"Tawa","tawa","c://a","kitchen","Swetha");
        List<ProductResponseDto> pList = new ArrayList<>();
        pList.add(p);
        Auction a=new Auction(1,new Date("2023/11/10 00:00:00"),new Date("2023/11/11 00:00:00"),1000.00);
        when(auctionServiceUtil.getProductDtlsByCategoty(Mockito.any())).thenReturn(pList);
        when(auctionRepository.findByProductId(Mockito.anyInt())).thenReturn(Optional.of(a));
//        when(auctionRepository.findActiveByCategory(Mockito.any(),Mockito.anyInt())).thenReturn(Optional.empty());
        assertTrue(auctionService.getActiveByCategory("Kitchen").isEmpty());

    }

}
