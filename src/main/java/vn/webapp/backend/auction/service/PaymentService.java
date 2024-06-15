package vn.webapp.backend.auction.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.config.VNPAYConfiguration;
import vn.webapp.backend.auction.dto.PaymentResponse;
import vn.webapp.backend.auction.service.vnpay.VNPayUtil;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final VNPAYConfiguration vnPayConfig;

    public PaymentResponse.VNPayResponse createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        String auctionId = request.getParameter("auctionId");
        String username = request.getParameter("username");
        String transactionId = request.getParameter("transactionId");
        Map<String, String> vnpParamsMap;

        if (transactionId != null) {
            int id = Integer.parseInt(transactionId);
            vnpParamsMap = vnPayConfig.getVNPayConfig(auctionId, username, id);
        } else {
            vnpParamsMap = vnPayConfig.getVNPayConfig(auctionId, username, 0);
        }
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        // Build query URL
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnpPayUrl() + "?" + queryUrl;

        return PaymentResponse.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }

}