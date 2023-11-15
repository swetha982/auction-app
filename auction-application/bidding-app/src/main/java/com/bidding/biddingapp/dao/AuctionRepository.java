package com.bidding.biddingapp.dao;

import com.bidding.biddingapp.model.Auction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends CrudRepository<Auction, Integer> {

    @Query(value="select a.* from auction a where end_time>:now", nativeQuery = true)
    List<Auction> findByTime(LocalDateTime now);

    @Query(value = "select a.* from auction a where a.product_id=:id",nativeQuery = true)
    Optional<Auction> findByProductId(int id);

    @Query(value = "select auc_id from auction where end_time> :now and auc_id=:auctionId",nativeQuery = true)
    Optional<Integer> findActiveByCategory(LocalDateTime now, Integer auctionId);

    @Query(value = "select auc_id from auction where product_id=:id", nativeQuery = true)
    Optional<Integer> findByProduct(int id);
}
