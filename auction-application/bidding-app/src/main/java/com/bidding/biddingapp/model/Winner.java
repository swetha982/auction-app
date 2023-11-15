package com.bidding.biddingapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="winners")
@Data
@NoArgsConstructor
public class Winner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "win_id")
    private Integer winnerId;

    private Integer productId;

    private Integer userId;

    private Integer auctionId;

    private Double basePrice;

    private Double winPrice;

    public Winner(Integer productId, Integer userId, Integer auctionId, Double basePrice,Double winPrice){
        this.productId = productId;
        this.userId = userId;
        this.auctionId = auctionId;
        this.basePrice = basePrice;
        this.winPrice = winPrice;
    }
}
