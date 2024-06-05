package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import vn.webapp.backend.auction.service.RealTimeService;

@RestController
@RequiredArgsConstructor
public class RealTimeController {

    private final RealTimeService realTimeService;

    @MessageMapping("/update-auction")
    @SendTo("/user/auction")
    public Double sendMessage(@Payload Integer auctionId) {
        return realTimeService.getLastPriceTogether(auctionId);
    }

}
