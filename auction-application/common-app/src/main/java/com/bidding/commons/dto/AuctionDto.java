package com.bidding.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDto {
    @com.fasterxml.jackson.annotation.JsonProperty("auctionId")
    private Integer auctionId;

    private ProductResponseDto productDtls;

    @com.fasterxml.jackson.annotation.JsonProperty("startTime")
    private Date startTime;

    @com.fasterxml.jackson.annotation.JsonProperty("endTime")
    private Date endTime;

    @com.fasterxml.jackson.annotation.JsonProperty("basePrice")
    private Double basePrice;
}
