package com.bidding.biddingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDTO {

    private String messageType;

    private String data;
}
