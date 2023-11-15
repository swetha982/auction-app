package com.bidding.clientApp.websocket;


import com.bidding.commons.dto.AuctionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Slf4j
public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    String userName="v@gmail.com";

    ObjectMapper objectMapper = new ObjectMapper();




    @Override
    public Type getPayloadType(StompHeaders headers) {
        return String.class;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("New session established : " + session.getSessionId());
        session.subscribe("/all", this);
        log.info("Subscribed");
        session.subscribe("/user/"+userName+"/win",this);
        //session.subscribe("/"+userName+"/client/win",this);
        log.info("Subscribed2");

    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.error("Got an exception", exception);
    }


    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        try {

            String val = headers.get("type").get(0);
            log.info("Inside handle frame"+ val);
            if(val.equals("normal")){
                showActiveAuctions(payload);
            }else{
                log.info("Winner Notification..........");
                String msg = (String)payload;
                log.info("Winner message...."+msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showActiveAuctions(Object payload) throws Exception {
        AuctionDto[] msg1=objectMapper.readValue((String) payload,AuctionDto[].class);
        for(AuctionDto a:msg1) {
            log.info("Received : " + a);
        }
    }


}
