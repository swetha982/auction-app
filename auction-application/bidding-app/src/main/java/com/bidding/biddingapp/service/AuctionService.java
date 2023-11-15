package com.bidding.biddingapp.service;


import com.bidding.commons.dto.AuctionDto;
import com.bidding.biddingapp.model.Auction;
import com.bidding.commons.dto.ProductResponseDto;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface AuctionService {

    public String createAuction(String productName, Date startTime, Date endTime, Double basePrice);

    public String updateAuction(Integer aucId, Integer productId, Date startTime, Date endTime, Double basePrice);

    String deleteAuction(Integer id);

    List<Auction> getActive();

    public List<AuctionDto> convertAuctionToResponse(List<Auction> auctions);

    List<AuctionDto> getActiveByCategory(String categoryName);

    public String createBid(Integer userId, Integer auctionId, Double amount);

    public List<Auction> getAllAuctions();

    public Set<String> selectWinner(Auction a);

    //public void sendActiveAuctionsToUser(List<AuctionDto> auctionList);
}
