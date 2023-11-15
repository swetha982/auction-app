package com.bidding.biddingapp.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="bids")
@Data
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_id")
    private int bidId;

    private Double amount;

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")*/
    private int user_id;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    public Bid(){

    }

    public Bid(Double amount, int uid){
        this.amount = amount;
        user_id=uid;
    }



}
