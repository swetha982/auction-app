package com.bidding.biddingapp.dao;

import com.bidding.biddingapp.model.Winner;
import org.springframework.data.repository.CrudRepository;

public interface WinnerRepository extends CrudRepository<Winner,Integer> {
}
