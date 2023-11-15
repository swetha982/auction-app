package com.bidding.biddingapp.dao;

import com.bidding.biddingapp.model.Bid;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface BidRepository extends CrudRepository<Bid,Integer> {

    //@Query(value = "select user_id from bids b where auction_id=:aucId and amount=(select max(amount) from bids where auction_id=:auc_Id)",nativeQuery = true)
    @Query(value = "select user_id from bids b where auction_id=:aucId and amount=:maxAmount",nativeQuery = true)
    Set<Integer> findWinner(Double maxAmount, Integer aucId);

    @Modifying
    @Query(value = "delete from bids where auction_id=:aucId",nativeQuery = true)
    void deleteBYAuctionId(Integer aucId);

    @Query(value = "select max(amount) from bids where auction_id=:aucId",nativeQuery = true)
    Double getMaxAmount(Integer aucId);
}
