package vn.webapp.backend.auction.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.PaymentResponse;
import vn.webapp.backend.auction.service.*;
import vn.webapp.backend.auction.service.vnpay.ResponseObject;

import java.io.IOException;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://fe-deploy-hazel.vercel.app"})
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class VNPAYController {

    private final PaymentService paymentService;
    private final AuctionRegistrationService auctionRegistrationService;
    private final TransactionService transactionService;

    @GetMapping("/vn-pay")
    public ResponseObject<PaymentResponse.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }

    @GetMapping("/vn-pay-callback")
    public void payCallbackHandler(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("auctionId") Integer auctionId, HttpServletResponse response) throws IOException {
        String status = request.getParameter("vnp_ResponseCode");
        String baseUrl = "https://fe-deploy-hazel.vercel.app/tai-san-dau-gia/";
        String redirectUrl = baseUrl + auctionId;
        if (!status.equals("00")) {
            redirectUrl += "?paymentStatus=failed";
        } else {
            auctionRegistrationService.registerUserForAuction(username, auctionId);
            redirectUrl += "?paymentStatus=success";
        }
        response.sendRedirect(redirectUrl);
    }

//    @GetMapping("pay-bill")
//    public String payBill(@PathParam("price") long price,@PathParam("id") Integer billId) throws UnsupportedEncodingException{
//
//        String vnp_Version = "2.1.0";
//        String vnp_Command = "pay";
//        String orderType = "other";
//        long amount = price*100;
//        String bankCode = "NCB";
//
//        String vnp_TxnRef = Config.getRandomNumber(8);
//        String vnp_IpAddr = "127.0.0.1";
//
//        String vnp_TmnCode = Config.vnp_TmnCode;
//
//        Map<String, String> vnp_Params = new HashMap<>();
//        vnp_Params.put("vnp_Version", vnp_Version);
//        vnp_Params.put("vnp_Command", vnp_Command);
//        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
//        vnp_Params.put("vnp_Amount", String.valueOf(amount));
//        vnp_Params.put("vnp_CurrCode", "VND");
//
//        vnp_Params.put("vnp_BankCode", bankCode);
//        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
//        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
//        vnp_Params.put("vnp_OrderType", orderType);
//
//        vnp_Params.put("vnp_Locale", "vn");
//        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl+"?billId="+billId);
//        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
//
//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        String vnp_CreateDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//
//        cld.add(Calendar.MINUTE, 15);
//        String vnp_ExpireDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//
//        List fieldNames = new ArrayList(vnp_Params.keySet());
//        Collections.sort(fieldNames);
//        StringBuilder hashData = new StringBuilder();
//        StringBuilder query = new StringBuilder();
//        Iterator itr = fieldNames.iterator();
//        while (itr.hasNext()) {
//            String fieldName = (String) itr.next();
//            String fieldValue = (String) vnp_Params.get(fieldName);
//            if ((fieldValue != null) && (fieldValue.length() > 0)) {
//                //Build hash data
//                hashData.append(fieldName);
//                hashData.append('=');
//                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                //Build query
//                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
//                query.append('=');
//                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                if (itr.hasNext()) {
//                    query.append('&');
//                    hashData.append('&');
//                }
//            }
//        }
//        String queryUrl = query.toString();
//        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
//        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
//        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
//
//        return paymentUrl;
//    }
//
//    @GetMapping("pay-service")
//    public String getPayService(@PathParam("price") long price,@PathParam("id") Integer registerServiceId) throws UnsupportedEncodingException{
//
//        String vnp_Version = "2.1.0";
//        String vnp_Command = "pay";
//        String orderType = "other";
//        long amount = price*100;
//        String bankCode = "NCB";
//
//        String vnp_TxnRef = Config.getRandomNumber(8);
//        String vnp_IpAddr = "127.0.0.1";
//
//        String vnp_TmnCode = Config.vnp_TmnCode;
//
//        Map<String, String> vnp_Params = new HashMap<>();
//        vnp_Params.put("vnp_Version", vnp_Version);
//        vnp_Params.put("vnp_Command", vnp_Command);
//        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
//        vnp_Params.put("vnp_Amount", String.valueOf(amount));
//        vnp_Params.put("vnp_CurrCode", "VND");
//
//        vnp_Params.put("vnp_BankCode", bankCode);
//        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
//        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
//        vnp_Params.put("vnp_OrderType", orderType);
//
//        vnp_Params.put("vnp_Locale", "vn");
//        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl+"?registerServiceId="+registerServiceId);
//        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
//
//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        String vnp_CreateDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//
//        cld.add(Calendar.MINUTE, 15);
//        String vnp_ExpireDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//
//        List fieldNames = new ArrayList(vnp_Params.keySet());
//        Collections.sort(fieldNames);
//        StringBuilder hashData = new StringBuilder();
//        StringBuilder query = new StringBuilder();
//        Iterator itr = fieldNames.iterator();
//        while (itr.hasNext()) {
//            String fieldName = (String) itr.next();
//            String fieldValue = (String) vnp_Params.get(fieldName);
//            if ((fieldValue != null) && (fieldValue.length() > 0)) {
//                //Build hash data
//                hashData.append(fieldName);
//                hashData.append('=');
//                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                //Build query
//                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
//                query.append('=');
//                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                if (itr.hasNext()) {
//                    query.append('&');
//                    hashData.append('&');
//                }
//            }
//        }
//        String queryUrl = query.toString();
//        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
//        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
//        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
//
//        return paymentUrl;
//    }

    //    @GetMapping("/vn-pay-callback/auctionId/{id}")
//    public ResponseObject<PaymentDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request, @PathVariable("id") String id) {
//        String status = request.getParameter("vnp_ResponseCode");
//        String baseUrl = "http://localhost:3000";
//        if (status.equals("00")) {
//            return new ResponseObject<>(HttpStatus.OK, "Success", new PaymentDTO.VNPayResponse("00", "Success", baseUrl + "/single-auction/" + id));
//        } else {
//            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", new PaymentDTO.VNPayResponse("99", "Failed", baseUrl + "/single-auction/" + id));
//        }
//    }

}