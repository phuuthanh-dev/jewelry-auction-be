package vn.webapp.backend.auction.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import vn.webapp.backend.auction.model.Auction;

@RestController
public class RealTimeController {

    @MessageMapping("/update-last-price")
    @SendTo("/topic/auction")
    public Auction sendMessage(@Payload Auction auction) {
        return auction;
    }

}
