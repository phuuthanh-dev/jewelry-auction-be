package vn.webapp.backend.auction.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailContent {

    public String setHtmlContent(String fullName, String buttonName, String url, String firstMessage,
                                 String secondMessage) {
        String imageUrl = "https://raw.githubusercontent.com/phuuthanh2003/AuctionWebApp_BE/main/logo.png";
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Activation Code</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #fff;\n" +
                "            padding: 40px;\n" +
                "            border-radius: 10px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #333!important;\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "        h3, h4, div {\n" +
                "            color: #000!important;" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "        .activation-code {\n" +
                "            background-color: #f0f0f0;\n" +
                "            padding: 10px;\n" +
                "            border-radius: 5px;\n" +
                "            font-size: 24px;\n" +
                "            font-weight: bold;\n" +
                "            text-align: center;\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "        .mt-20 {\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "        .btn {\n" +
                "            display: inline-block;\n" +
                "            padding: 15px 30px;\n" +
                "            font-size: 16px;\n" +
                "            color: #fff!important;\n" +
                "            background-color: #007BFF;\n" +
                "            border-radius: 5px;\n" +
                "            text-decoration: none;\n" +
                "            text-align: center;\n" +
                "            margin: 20px 0;\n" +
                "        }\n" +
                "        .logo {\n" +
                "            text-align: center;\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"logo\">\n" +
                "            <img src=\"" + imageUrl + "\" alt=\"Company Logo\" width=\"200\">\n" +
                "        </div>\n" +
                "        <h3>Xin chào, " + fullName + "</h3>\n" +
                "        <h4>" + firstMessage + "</h4>\n" +
                "        <div>" + secondMessage + "</div>\n" +
                "        <a href=\"" + url + "\" class=\"btn\">" + buttonName + "</a>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
        return content;
    }

    public String setHtmlConfirmHoldingContent(String fullName, String assetName
                                 ) {
        String imageUrl = "https://raw.githubusercontent.com/phuuthanh2003/AuctionWebApp_BE/main/logo.png";
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Activation Code</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #fff;\n" +
                "            padding: 40px;\n" +
                "            border-radius: 10px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #333!important;\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "        h3, h4, div {\n" +
                "            color: #000!important;" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "        .activation-code {\n" +
                "            background-color: #f0f0f0;\n" +
                "            padding: 10px;\n" +
                "            border-radius: 5px;\n" +
                "            font-size: 24px;\n" +
                "            font-weight: bold;\n" +
                "            text-align: center;\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "        .mt-20 {\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "        .btn {\n" +
                "            display: inline-block;\n" +
                "            padding: 15px 30px;\n" +
                "            font-size: 16px;\n" +
                "            color: #fff!important;\n" +
                "            background-color: #007BFF;\n" +
                "            border-radius: 5px;\n" +
                "            text-decoration: none;\n" +
                "            text-align: center;\n" +
                "            margin: 20px 0;\n" +
                "        }\n" +
                "        .logo {\n" +
                "            text-align: center;\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"logo\">\n" +
                "            <img src=\"" + imageUrl + "\" alt=\"Company Logo\" width=\"200\">\n" +
                "        </div>\n" +
                "        <h3>Xin chào, " + fullName + "</h3>\n" +
                "        <h4>Chúng tôi đã nhận được tài sản " + assetName +   " được gửi từ bạn</h4>\n" +
                "        <h4>Phiên đấu giá cho tài sản sẽ được hệ thống khởi tạo trong thời gian sớm nhất.</h4>\n" +
                "        <h4>Theo dõi thông tin sàn sản của bạn tại mục THÔNG TIN CÁ NHÂN >> TÀI SẢN CỦA TÔI.</h4>\n" +
                "        <h5>Cảm ơn bạn đã tin tưởng và đồng hành cùng DGS. Mọi thắc mắc xin liên hệ (+84) 0123456789 để được hỗ trợ và tư vấn.</h5>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
        return content;
    }
}
