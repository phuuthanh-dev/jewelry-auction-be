package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import vn.webapp.backend.auction.service.RealTimeService;

import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class RealTimeController {

    private final RealTimeService realTimeService;
    Logger logger = Logger.getLogger(getClass().getName());


    @MessageMapping("/update-auction")
    @SendTo("/user/auction")
    public Double sendMessage(@Payload Integer auctionId) {
        return realTimeService.getLastPriceTogether(auctionId);
    }

    @MessageExceptionHandler
    public void handleException(Exception ex) {
        logger.info("An error occurred: " + ex.getMessage());
    }
}
