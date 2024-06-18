package vn.webapp.backend.auction.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.config.frontend.FrontendConfiguration;
import vn.webapp.backend.auction.dto.PaymentResponse;
import vn.webapp.backend.auction.service.*;
import vn.webapp.backend.auction.service.vnpay.ResponseObject;

import java.io.IOException;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://fe-deploy-hazel.vercel.app"})
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class VNPAYController {

    private final FrontendConfiguration frontendConfiguration;
    private final PaymentService paymentService;
    private final AuctionRegistrationService auctionRegistrationService;
    private final TransactionService transactionService;

    @GetMapping("/vn-pay")
    public ResponseObject<PaymentResponse.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }

    @GetMapping("/vn-pay-callback")
    public void payCallbackHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("username") String username,
            @RequestParam("auctionId") Integer auctionId,
            @RequestParam("transactionId") Integer transactionId
    ) throws IOException {
        String status = request.getParameter("vnp_ResponseCode");
        String redirectUrl = "";

        if (transactionId == 0) {
            redirectUrl = handleAuctionPaymentCallback(username, auctionId, status);
        } else {
            redirectUrl = handleTransactionPaymentCallback(transactionId, status);
        }
        response.sendRedirect(redirectUrl);
    }

    private String handleAuctionPaymentCallback(String username, Integer auctionId, String status) {
        String baseUrl = frontendConfiguration.getBaseUrl() + "/tai-san-dau-gia/";
        String redirectUrl = baseUrl + auctionId;

        if (!status.equals("00")) {
            redirectUrl += "?paymentStatus=failed";
        } else {
            auctionRegistrationService.registerUserForAuction(username, auctionId);
            redirectUrl += "?paymentStatus=success";
        }

        return redirectUrl;
    }

    private String handleTransactionPaymentCallback(Integer transactionId, String status) {
        String redirectUrl = frontendConfiguration.getBaseUrl() + "/thong-tin-ca-nhan/";

        if (!status.equals("00")) {
            redirectUrl += "?paymentStatus=failed";
        } else {
            transactionService.setTransactionState(transactionId, "SUCCEED");
            transactionService.setTransactionMethod(transactionId, "BANKING");
            redirectUrl += "?paymentStatus=success";
        }

        return redirectUrl;
    }

}