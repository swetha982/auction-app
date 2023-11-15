package com.bidding.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BiddingRequest {
    private Integer userId;
    private Integer auctionId;
    private Double amount;
}
