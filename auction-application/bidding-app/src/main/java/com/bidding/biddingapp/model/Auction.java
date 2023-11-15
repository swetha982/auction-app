package com.bidding.biddingapp.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name="auction")
@Entity
@Data
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auc_id")
    private Integer auctionId;

    private Integer productId;

    private Date startTime;

    private Date endTime;

    private Double basePrice;

    @OneToMany(mappedBy="auction", cascade=CascadeType.ALL)
    private List<Bid> bids;

    public Auction(){}

    public Auction(Integer productId,Date startTime, Date endTime, Double basePrice){
        this.productId=productId;
        this.startTime=startTime;
        this.endTime=endTime;
        this.basePrice=basePrice;
    }


}
