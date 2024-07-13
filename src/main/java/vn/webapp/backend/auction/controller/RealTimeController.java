package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import vn.webapp.backend.auction.dto.BidMessage;
import vn.webapp.backend.auction.dto.BidResponse;
import vn.webapp.backend.auction.dto.KickOutMessage;
import vn.webapp.backend.auction.dto.KickOutResponse;
import vn.webapp.backend.auction.model.AuctionRegistration;
import vn.webapp.backend.auction.service.realtime.RealTimeService;

import java.util.Map;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class RealTimeController {

    private final RealTimeService realTimeService;
    Logger logger = Logger.getLogger(getClass().getName());


    @MessageMapping("/update-auction")
    @SendTo("/user/auction")
    public BidResponse sendMessage(@Payload BidMessage message) {
        Integer auctionId = message.auctionId();
        Long bonusTime = message.bonusTime();
        String username = message.username();

        return realTimeService.getLastPriceTogether(auctionId, bonusTime, username);
    }

    @MessageMapping("/kick-out-user")
    @SendTo("/user/out-auction-registration")
    public KickOutResponse sendMessage(@Payload KickOutMessage message) {
        Integer auctionId = message.auctionId();
        String username = message.username();
        return realTimeService.staffKickOutMember(auctionId, username);
    }

    @MessageExceptionHandler
    public void handleException(Exception ex) {
        logger.info("An error occurred: " + ex.getMessage());
    }
}
