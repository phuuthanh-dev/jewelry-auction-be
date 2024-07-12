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
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "  <title>Email DGS</title>\n" +
                "  <link rel=\"important stylesheet\" href=\"chrome://messagebody/skin/messageBody.css\">\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "  <br>\n" +
                "  <!DOCTYPE html>\n" +
                "  <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "\n" +
                "  <head>\n" +
                "    <style title=\"not-inlined\">\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      .address a {\n" +
                "        text-decoration: none !important;\n" +
                "        color: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      @media only screen and (max-device-width: 600px) {\n" +
                "\n" +
                "        table[class~=fullwidth],\n" +
                "        td[class~=fullwidth] {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        table[class~=halfwidth],\n" +
                "        td[class~=halfwidth] {\n" +
                "          width: 50% !important;\n" +
                "        }\n" +
                "\n" +
                "        table[class~=nested-fullwidth],\n" +
                "        td[class~=nested-fullwidth] {\n" +
                "          width: 87% !important;\n" +
                "        }\n" +
                "\n" +
                "        table[class~=nested-halfwidth],\n" +
                "        td[class~=nested-halfwidth] {\n" +
                "          width: 33% !important;\n" +
                "        }\n" +
                "\n" +
                "        table[class~=hide],\n" +
                "        td[class~=hide],\n" +
                "        p[class~=hide],\n" +
                "        br[class~=hide],\n" +
                "        img[class~=hide],\n" +
                "        span[class~=hide],\n" +
                "        div[class~=hide] {\n" +
                "          display: none !important;\n" +
                "        }\n" +
                "\n" +
                "      }\n" +
                "    </style>\n" +
                "    <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />\n" +
                "    <meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\" />\n" +
                "    <meta content=\"telephone=no\" name=\"format-detection\" />\n" +
                "    <title>DGS Email</title>\n" +
                "  </head>\n" +
                "\n" +
                "  <body bgcolor=\"#F5F5F5\" leftmargin=\"0\" marginheight=\"0\" marginwidth=\"0\"\n" +
                "    style=\"width: 100% !important;-webkit-text-size-adjust: 100%;-ms-text-size-adjust: 100%;margin: 0;padding: 0;\"\n" +
                "    topmargin=\"0\">\n" +
                "    <div\n" +
                "      style=\"color:transparent;visibility:hidden;opacity:0;font-size:0px;border:0;max-height:1px;width:1px;margin:0px;padding:0px;border-width:0px!important;display:none!important;line-height:0px!important;\">\n" +
                "    </div>\n" +
                "    <table align=\"center\" bgcolor=\"#F5F5F5\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"background-table\"\n" +
                "      style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;margin: 0;padding: 0;width: 100% !important;line-height: 100% !important;background-color: #f5f5f5;font-family: Helvetica, Arial, sans-serif;font-size: 13px;color: #555555;\"\n" +
                "      width=\"100%\">\n" +
                "      <tbody>\n" +
                "        <tr>\n" +
                "          <td valign=\"top\">\n" +
                "            <table align=\"center\" bgcolor=\"#E5E5E5\" border=\"0\" cellpadding=\"20\" cellspacing=\"0\" class=\"fullwidth\"\n" +
                "              id=\"header\"\n" +
                "              style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;background: #e5e5e5;\"\n" +
                "              width=\"600\">\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td width=\"183\">\n" +
                "                    <img alt=\"SourceForge\" class=\"image-fix\" height=\"auto\"\n" +
                "                      src=\"" +imageUrl +"\"\n" +
                "                      style=\"max-width: 200px;font-size: 20px;line-height: 29px;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;border: none;display: block;color: #333333\"\n" +
                "                      title=\"SourceForge\" width=\"200\" />\n" +
                "                  </td>\n" +
                "                  <td align=\"right\" class=\"hide\" width=\"417\">\n" +
                "                    <p id=\"tagline\"\n" +
                "                      style=\"font-family: Helvetica, Arial, sans-serif;color: #555555;line-height: 2rem;margin: 0;font-style: italic;font-size: 11px;\">\n" +
                "                      <b>Nền tảng Đấu giá Trang sức</b> hàng đầu <b>Việt Nam</b>.\n" +
                "                    </p>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "            <table align=\"center\" bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"20\" cellspacing=\"0\" class=\"fullwidth\"\n" +
                "              style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\" width=\"600\">\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td bgcolor=\"#333333\" class=\"hide\" id=\"headline\"\n" +
                "                    style=\"background-color: #333333; border-top: 4px solid #fed100; border-bottom: 4px solid #fed100\"\n" +
                "                    width=\"600\" height=\"10\">\n" +
                "                    <h1\n" +
                "                      style=\"font-family: Helvetica, Arial, sans-serif;font-size: 18px;margin: 0;line-height: 1.5em;color: #ffffff !important;\">\n" +
                "                      "+ buttonName +"</h1>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td class=\"fullwidth\" width=\"600\">\n" +
                "                    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"nested-fullwidth\"\n" +
                "                      id=\"project-header\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"\n" +
                "                      width=\"560\">\n" +
                "                      <tbody>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "                    <br class=\"spacer\" style=\"line-height: 20px;\" />\n" +
                "                    <h3>Xin chào, " + fullName + "</h3>\n" +
                "                    <h4>" + firstMessage + "</h4>\n" +
                "                    <div>" + secondMessage + "</div>\n <br/>" +
                "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" " +
                "                    style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\" width=\"260\">" +
                "                    <tbody>" +
                "                    <tr>" +
                "                    <td align=\"center\" bgcolor=\"#339933\" class=\"button-wrapper\" height=\"43\" " +
                "                    style=\"background: transparent none no-repeat 0 0 !important;\" valign=\"middle\" width=\"260\">" +
                "                    <a class=\"download-button button\" " +
                "                    href=\"" + url +"\" " +
                "                    style=\"color: #ffffff;text-decoration: none;font-family: Helvetica, Arial, sans-serif;font-size: 18px;text-align: center;line-height: 43px;border-radius: 4px;display: block;min-height: 43px;background-color: #fed100;\" " +
                "                    target=\"_blank\" title=\"Download Latest Version\"><span " +
                "                    style=\"font-family: Helvetica, Arial, sans-serif;color: black;font-size: 18px;text-align: center;line-height: 43px;text-decoration: none;\">" + buttonName + " </a>" +
                "                    </td>" +
                "                    </tr>" +
                "                    </tbody>" +
                "                    </table>" +
                "                    <br class=\"spacer\" style=\"line-height: 20px;\" />\n" +
                "                    <br class=\"spacer\" style=\"line-height: 20px;\" />\n" +
                "\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "            <table align=\"center\" bgcolor=\"#E5E5E5\" border=\"0\" cellpadding=\"20\" cellspacing=\"0\" class=\"fullwidth\"\n" +
                "              id=\"footer\"\n" +
                "              style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;background-color: #e5e5e5; border-top: 4px solid #fed100\"\n" +
                "              width=\"600\">\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td class=\"fullwidth\" id=\"legalese\" width=\"547\">\n" +
                "                    <p\n" +
                "                      style=\"margin: 0;font-family: Helvetica, Arial, sans-serif;color: #555555;line-height: 1.5em;font-size: 11px;\">\n" +
                "                      Mọi thắc mắc xin liên hệ (+84) 0123456789 để được hỗ trợ và tư vấn.</p>\n" +
                "                    <p\n" +
                "                      style=\"margin: 0;font-family: Helvetica, Arial, sans-serif;color: #555555;line-height: 1.5em;font-size: 11px;\">\n" +
                "                      <a href=\"mailto:daugia.dgs789@gmail.com\" style=\"text-decoration: underline;color: #666666;\"\n" +
                "                        target=\"_blank\" title=\"Unsubscribe from My-Project-Files\">Email: daugia.dgs789@gmail.com</a>\n" +
                "                      <br />\n" +
                "                      <a href=\"#\" style=\"text-decoration: underline;color: #666666;\" target=\"_blank\"\n" +
                "                        title=\"Manage your subscriptions\"> Hotline: (+84) 0123456789</a>\n" +
                "                      |\n" +
                "                      <a href=\"#\" style=\"text-decoration: underline;color: #666666;\" target=\"_blank\"\n" +
                "                        title=\"Review SourceForge's privacy policy\">Đấu giá DGS</a>\n" +
                "                    </p>\n" +
                "                    <p class=\"address\"\n" +
                "                      style=\"margin: 0;font-family: Helvetica, Arial, sans-serif;color: #555555;line-height: 1.5em;font-size: 11px;\">\n" +
                "                      Lưu Hữu Phước, Đông Hoà, Dĩ An, Bình Dương, Việt Nam</p>\n" +
                "                  </td>\n" +
                "                  <td align=\"right\" class=\"hide\" width=\"53\">\n" +
                "                    <a href=\"https://l.sourceforge.net/f/a/dBA2aK25KAt4dnvzCZ7CtQ~~/AABcDgA~/RgRoCA6JP0QXaHR0cHM6Ly9zb3VyY2Vmb3JnZS5uZXRXA3NwY0IKZiIJ2yZmX0FUblIQdHJjZmlyQGdtYWlsLmNvbVgEAAAAAQ~~\"\n" +
                "                      style=\"text-decoration: underline;color: #666666;\" target=\"_blank\" title=\"SourceForge\">\n" +
                "                      <img alt=\"SF Logo\" class=\"image-fix\" height=\"49\"\n" +
                "                        src=\"https://raw.githubusercontent.com/huyhoang-doit/AuctionWebApp_FE/master/website/public/assets/images/favicon.ico\"\n" +
                "                        style=\"max-width: 56px;font-size: 14px;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;border: none;display: block;color: #333333\"\n" +
                "                        title=\"SF\" width=\"56\" />\n" +
                "                    </a>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </tbody>\n" +
                "    </table>\n" +
                "    <!-- End of wrapper table -->\n" +
                "\n" +
                "    <img border=\"0\" width=\"1\" height=\"1\" alt=\"\"\n" +
                "      src=\"https://l.sourceforge.net/q/3xRj8PDFMzdZv3oiy3pleQ~~/AABcDgA~/RgRoCA6JPlcDc3BjQgpmIgnbJmZfQVRuUhB0cmNmaXJAZ21haWwuY29tWAQAAAAB\">\n" +
                "  </body>\n" +
                "\n" +
                "  </html>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n" +
                "</table>\n" +
                "</div>";
        return content;
    }

    public String setHtmlConfirmHoldingContent(String fullName, String assetName
                                 ) {
        String imageUrl = "https://raw.githubusercontent.com/phuuthanh2003/AuctionWebApp_BE/main/logo.png";
        String content = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "  <title>Email DGS</title>\n" +
                "  <link rel=\"important stylesheet\" href=\"chrome://messagebody/skin/messageBody.css\">\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "  <br>\n" +
                "  <!DOCTYPE html>\n" +
                "  <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "\n" +
                "  <head>\n" +
                "    <style title=\"not-inlined\">\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      .address a {\n" +
                "        text-decoration: none !important;\n" +
                "        color: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      @media only screen and (max-device-width: 600px) {\n" +
                "\n" +
                "        table[class~=fullwidth],\n" +
                "        td[class~=fullwidth] {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        table[class~=halfwidth],\n" +
                "        td[class~=halfwidth] {\n" +
                "          width: 50% !important;\n" +
                "        }\n" +
                "\n" +
                "        table[class~=nested-fullwidth],\n" +
                "        td[class~=nested-fullwidth] {\n" +
                "          width: 87% !important;\n" +
                "        }\n" +
                "\n" +
                "        table[class~=nested-halfwidth],\n" +
                "        td[class~=nested-halfwidth] {\n" +
                "          width: 33% !important;\n" +
                "        }\n" +
                "\n" +
                "        table[class~=hide],\n" +
                "        td[class~=hide],\n" +
                "        p[class~=hide],\n" +
                "        br[class~=hide],\n" +
                "        img[class~=hide],\n" +
                "        span[class~=hide],\n" +
                "        div[class~=hide] {\n" +
                "          display: none !important;\n" +
                "        }\n" +
                "\n" +
                "      }\n" +
                "    </style>\n" +
                "    <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />\n" +
                "    <meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\" />\n" +
                "    <meta content=\"telephone=no\" name=\"format-detection\" />\n" +
                "    <title>DGS Email</title>\n" +
                "  </head>\n" +
                "\n" +
                "  <body bgcolor=\"#F5F5F5\" leftmargin=\"0\" marginheight=\"0\" marginwidth=\"0\"\n" +
                "    style=\"width: 100% !important;-webkit-text-size-adjust: 100%;-ms-text-size-adjust: 100%;margin: 0;padding: 0;\"\n" +
                "    topmargin=\"0\">\n" +
                "    <div\n" +
                "      style=\"color:transparent;visibility:hidden;opacity:0;font-size:0px;border:0;max-height:1px;width:1px;margin:0px;padding:0px;border-width:0px!important;display:none!important;line-height:0px!important;\">\n" +
                "    </div>\n" +
                "    <table align=\"center\" bgcolor=\"#F5F5F5\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"background-table\"\n" +
                "      style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;margin: 0;padding: 0;width: 100% !important;line-height: 100% !important;background-color: #f5f5f5;font-family: Helvetica, Arial, sans-serif;font-size: 13px;color: #555555;\"\n" +
                "      width=\"100%\">\n" +
                "      <tbody>\n" +
                "        <tr>\n" +
                "          <td valign=\"top\">\n" +
                "            <table align=\"center\" bgcolor=\"#E5E5E5\" border=\"0\" cellpadding=\"20\" cellspacing=\"0\" class=\"fullwidth\"\n" +
                "              id=\"header\"\n" +
                "              style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;background: #e5e5e5;\"\n" +
                "              width=\"600\">\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td width=\"183\">\n" +
                "                    <img alt=\"SourceForge\" class=\"image-fix\" height=\"auto\"\n" +
                "                      src=\"" +imageUrl +"\"\n" +
                "                      style=\"max-width: 200px;font-size: 20px;line-height: 29px;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;border: none;display: block;color: #333333\"\n" +
                "                      title=\"SourceForge\" width=\"200\" />\n" +
                "                  </td>\n" +
                "                  <td align=\"right\" class=\"hide\" width=\"417\">\n" +
                "                    <p id=\"tagline\"\n" +
                "                      style=\"font-family: Helvetica, Arial, sans-serif;color: #555555;line-height: 2rem;margin: 0;font-style: italic;font-size: 11px;\">\n" +
                "                      <b>Nền tảng Đấu giá Trang sức</b> hàng đầu <b>Việt Nam</b>.\n" +
                "                    </p>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "            <table align=\"center\" bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"20\" cellspacing=\"0\" class=\"fullwidth\"\n" +
                "              style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\" width=\"600\">\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td bgcolor=\"#333333\" class=\"hide\" id=\"headline\"\n" +
                "                    style=\"background-color: #333333; border-top: 4px solid #fed100; border-bottom: 4px solid #fed100\"\n" +
                "                    width=\"600\" height=\"10\">\n" +
                "                    <h1\n" +
                "                      style=\"font-family: Helvetica, Arial, sans-serif;font-size: 18px;margin: 0;line-height: 1.5em;color: #ffffff !important;\">\n" +
                "                      Chúng tôi đã nhận tài sản từ bạn.</h1>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td class=\"fullwidth\" width=\"600\">\n" +
                "                    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"nested-fullwidth\"\n" +
                "                      id=\"project-header\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"\n" +
                "                      width=\"560\">\n" +
                "                      <tbody>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "                    <br class=\"spacer\" style=\"line-height: 20px;\" />\n" +
                "                    <h3>Xin chào, \"" + fullName + " \"</h3>\n" +
                "                    <h4>Chúng tôi đã nhận được tài sản \" " + assetName + " \" được gửi từ bạn</h4>\n" +
                "                    <h4>Phiên đấu giá cho tài sản sẽ được hệ thống khởi tạo trong thời gian sớm nhất.</h4>\n" +
                "                    <h4>Theo dõi thông tin sàn sản của bạn tại mục THÔNG TIN CÁ NHÂN >> TÀI SẢN CỦA TÔI.</h4>\n" +
                "                    <br class=\"spacer\" style=\"line-height: 20px;\" />\n" +
                "                    <br class=\"spacer\" style=\"line-height: 20px;\" />\n" +
                "\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "            <table align=\"center\" bgcolor=\"#E5E5E5\" border=\"0\" cellpadding=\"20\" cellspacing=\"0\" class=\"fullwidth\"\n" +
                "              id=\"footer\"\n" +
                "              style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;background-color: #e5e5e5; border-top: 4px solid #fed100\"\n" +
                "              width=\"600\">\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td class=\"fullwidth\" id=\"legalese\" width=\"547\">\n" +
                "                    <p\n" +
                "                      style=\"margin: 0;font-family: Helvetica, Arial, sans-serif;color: #555555;line-height: 1.5em;font-size: 11px;\">\n" +
                "                      Mọi thắc mắc xin liên hệ (+84) 0123456789 để được hỗ trợ và tư vấn.</p>\n" +
                "                    <p\n" +
                "                      style=\"margin: 0;font-family: Helvetica, Arial, sans-serif;color: #555555;line-height: 1.5em;font-size: 11px;\">\n" +
                "                      <a href=\"mailto:daugia.dgs789@gmail.com\" style=\"text-decoration: underline;color: #666666;\"\n" +
                "                        target=\"_blank\" title=\"Unsubscribe from My-Project-Files\">Email: daugia.dgs789@gmail.com</a>\n" +
                "                      <br />\n" +
                "                      <a href=\"#\" style=\"text-decoration: underline;color: #666666;\" target=\"_blank\"\n" +
                "                        title=\"Manage your subscriptions\"> Hotline: (+84) 0123456789</a>\n" +
                "                      |\n" +
                "                      <a href=\"#\" style=\"text-decoration: underline;color: #666666;\" target=\"_blank\"\n" +
                "                        title=\"Review SourceForge's privacy policy\">Đấu giá DGS</a>\n" +
                "                    </p>\n" +
                "                    <p class=\"address\"\n" +
                "                      style=\"margin: 0;font-family: Helvetica, Arial, sans-serif;color: #555555;line-height: 1.5em;font-size: 11px;\">\n" +
                "                      Lưu Hữu Phước, Đông Hoà, Dĩ An, Bình Dương, Việt Nam</p>\n" +
                "                  </td>\n" +
                "                  <td align=\"right\" class=\"hide\" width=\"53\">\n" +
                "                    <a href=\"https://l.sourceforge.net/f/a/dBA2aK25KAt4dnvzCZ7CtQ~~/AABcDgA~/RgRoCA6JP0QXaHR0cHM6Ly9zb3VyY2Vmb3JnZS5uZXRXA3NwY0IKZiIJ2yZmX0FUblIQdHJjZmlyQGdtYWlsLmNvbVgEAAAAAQ~~\"\n" +
                "                      style=\"text-decoration: underline;color: #666666;\" target=\"_blank\" title=\"SourceForge\">\n" +
                "                      <img alt=\"SF Logo\" class=\"image-fix\" height=\"49\"\n" +
                "                        src=\"https://raw.githubusercontent.com/huyhoang-doit/AuctionWebApp_FE/master/website/public/assets/images/favicon.ico\"\n" +
                "                        style=\"max-width: 56px;font-size: 14px;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;border: none;display: block;color: #333333\"\n" +
                "                        title=\"SF\" width=\"56\" />\n" +
                "                    </a>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </tbody>\n" +
                "    </table>\n" +
                "    <!-- End of wrapper table -->\n" +
                "\n" +
                "    <img border=\"0\" width=\"1\" height=\"1\" alt=\"\"\n" +
                "      src=\"https://l.sourceforge.net/q/3xRj8PDFMzdZv3oiy3pleQ~~/AABcDgA~/RgRoCA6JPlcDc3BjQgpmIgnbJmZfQVRuUhB0cmNmaXJAZ21haWwuY29tWAQAAAAB\">\n" +
                "  </body>\n" +
                "\n" +
                "  </html>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n" +
                "</table>\n" +
                "</div>";

        return content;
    }

    public String setHtmlBlockAccountContent(String fullName, String userName, String reason
    ) {
        String imageUrl = "https://raw.githubusercontent.com/phuuthanh2003/AuctionWebApp_BE/main/logo.png";
        String content = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "  <title>Email DGS</title>\n" +
                "  <link rel=\"important stylesheet\" href=\"chrome://messagebody/skin/messageBody.css\">\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "  <br>\n" +
                "  <!DOCTYPE html>\n" +
                "  <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "\n" +
                "  <head>\n" +
                "    <style title=\"not-inlined\">\n" +
                "      table td {\n" +
                "        border-collapse: collapse;\n" +
                "      }\n" +
                "\n" +
                "      .address a {\n" +
                "        text-decoration: none !important;\n" +
                "        color: inherit !important;\n" +
                "      }\n" +
                "\n" +
                "      @media only screen and (max-device-width: 600px) {\n" +
                "\n" +
                "        table[class~=fullwidth],\n" +
                "        td[class~=fullwidth] {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        table[class~=halfwidth],\n" +
                "        td[class~=halfwidth] {\n" +
                "          width: 50% !important;\n" +
                "        }\n" +
                "\n" +
                "        table[class~=nested-fullwidth],\n" +
                "        td[class~=nested-fullwidth] {\n" +
                "          width: 87% !important;\n" +
                "        }\n" +
                "\n" +
                "        table[class~=nested-halfwidth],\n" +
                "        td[class~=nested-halfwidth] {\n" +
                "          width: 33% !important;\n" +
                "        }\n" +
                "\n" +
                "        table[class~=hide],\n" +
                "        td[class~=hide],\n" +
                "        p[class~=hide],\n" +
                "        br[class~=hide],\n" +
                "        img[class~=hide],\n" +
                "        span[class~=hide],\n" +
                "        div[class~=hide] {\n" +
                "          display: none !important;\n" +
                "        }\n" +
                "\n" +
                "      }\n" +
                "    </style>\n" +
                "    <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />\n" +
                "    <meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\" />\n" +
                "    <meta content=\"telephone=no\" name=\"format-detection\" />\n" +
                "    <title>DGS Email</title>\n" +
                "  </head>\n" +
                "\n" +
                "  <body bgcolor=\"#F5F5F5\" leftmargin=\"0\" marginheight=\"0\" marginwidth=\"0\"\n" +
                "    style=\"width: 100% !important;-webkit-text-size-adjust: 100%;-ms-text-size-adjust: 100%;margin: 0;padding: 0;\"\n" +
                "    topmargin=\"0\">\n" +
                "    <div\n" +
                "      style=\"color:transparent;visibility:hidden;opacity:0;font-size:0px;border:0;max-height:1px;width:1px;margin:0px;padding:0px;border-width:0px!important;display:none!important;line-height:0px!important;\">\n" +
                "    </div>\n" +
                "    <table align=\"center\" bgcolor=\"#F5F5F5\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"background-table\"\n" +
                "      style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;margin: 0;padding: 0;width: 100% !important;line-height: 100% !important;background-color: #f5f5f5;font-family: Helvetica, Arial, sans-serif;font-size: 13px;color: #555555;\"\n" +
                "      width=\"100%\">\n" +
                "      <tbody>\n" +
                "        <tr>\n" +
                "          <td valign=\"top\">\n" +
                "            <table align=\"center\" bgcolor=\"#E5E5E5\" border=\"0\" cellpadding=\"20\" cellspacing=\"0\" class=\"fullwidth\"\n" +
                "              id=\"header\"\n" +
                "              style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;background: #e5e5e5;\"\n" +
                "              width=\"600\">\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td width=\"183\">\n" +
                "                    <img alt=\"SourceForge\" class=\"image-fix\" height=\"auto\"\n" +
                "                      src=\"" +imageUrl +"\"\n" +
                "                      style=\"max-width: 200px;font-size: 20px;line-height: 29px;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;border: none;display: block;color: #333333\"\n" +
                "                      title=\"SourceForge\" width=\"200\" />\n" +
                "                  </td>\n" +
                "                  <td align=\"right\" class=\"hide\" width=\"417\">\n" +
                "                    <p id=\"tagline\"\n" +
                "                      style=\"font-family: Helvetica, Arial, sans-serif;color: #555555;line-height: 2rem;margin: 0;font-style: italic;font-size: 11px;\">\n" +
                "                      <b>Nền tảng Đấu giá Trang sức</b> hàng đầu <b>Việt Nam</b>.\n" +
                "                    </p>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "            <table align=\"center\" bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"20\" cellspacing=\"0\" class=\"fullwidth\"\n" +
                "              style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\" width=\"600\">\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td bgcolor=\"#333333\" class=\"hide\" id=\"headline\"\n" +
                "                    style=\"background-color: #333333; border-top: 4px solid #fed100; border-bottom: 4px solid #fed100\"\n" +
                "                    width=\"600\" height=\"10\">\n" +
                "                    <h1\n" +
                "                      style=\"font-family: Helvetica, Arial, sans-serif;font-size: 18px;margin: 0;line-height: 1.5em;color: #ffffff !important;\">\n" +
                "                      Thông báo khóa tài khoản người dùng.</h1>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td class=\"fullwidth\" width=\"600\">\n" +
                "                    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"nested-fullwidth\"\n" +
                "                      id=\"project-header\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"\n" +
                "                      width=\"560\">\n" +
                "                      <tbody>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "                    <br class=\"spacer\" style=\"line-height: 20px;\" />\n" +
                "        <h3>Xin chào, " + fullName + "</h3>\n" +
                "        <h4>Rất tiếc phải thông báo Tài khoản  " + userName +   " đã bị khóa.</h4>\n" +
                "        <h4><strong>Lý do: </strong> "+ reason +"</h4>\n" +
                "                    <br class=\"spacer\" style=\"line-height: 20px;\" />\n" +
                "                    <br class=\"spacer\" style=\"line-height: 20px;\" />\n" +
                "\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "            <table align=\"center\" bgcolor=\"#E5E5E5\" border=\"0\" cellpadding=\"20\" cellspacing=\"0\" class=\"fullwidth\"\n" +
                "              id=\"footer\"\n" +
                "              style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;background-color: #e5e5e5; border-top: 4px solid #fed100\"\n" +
                "              width=\"600\">\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td class=\"fullwidth\" id=\"legalese\" width=\"547\">\n" +
                "                    <p\n" +
                "                      style=\"margin: 0;font-family: Helvetica, Arial, sans-serif;color: #555555;line-height: 1.5em;font-size: 11px;\">\n" +
                "                      Mọi thắc mắc xin liên hệ (+84) 0123456789 để được hỗ trợ và tư vấn.</p>\n" +
                "                    <p\n" +
                "                      style=\"margin: 0;font-family: Helvetica, Arial, sans-serif;color: #555555;line-height: 1.5em;font-size: 11px;\">\n" +
                "                      <a href=\"mailto:daugia.dgs789@gmail.com\" style=\"text-decoration: underline;color: #666666;\"\n" +
                "                        target=\"_blank\" title=\"Unsubscribe from My-Project-Files\">Email: daugia.dgs789@gmail.com</a>\n" +
                "                      <br />\n" +
                "                      <a href=\"#\" style=\"text-decoration: underline;color: #666666;\" target=\"_blank\"\n" +
                "                        title=\"Manage your subscriptions\"> Hotline: (+84) 0123456789</a>\n" +
                "                      |\n" +
                "                      <a href=\"#\" style=\"text-decoration: underline;color: #666666;\" target=\"_blank\"\n" +
                "                        title=\"Review SourceForge's privacy policy\">Đấu giá DGS</a>\n" +
                "                    </p>\n" +
                "                    <p class=\"address\"\n" +
                "                      style=\"margin: 0;font-family: Helvetica, Arial, sans-serif;color: #555555;line-height: 1.5em;font-size: 11px;\">\n" +
                "                      Lưu Hữu Phước, Đông Hoà, Dĩ An, Bình Dương, Việt Nam</p>\n" +
                "                  </td>\n" +
                "                  <td align=\"right\" class=\"hide\" width=\"53\">\n" +
                "                    <a href=\"https://l.sourceforge.net/f/a/dBA2aK25KAt4dnvzCZ7CtQ~~/AABcDgA~/RgRoCA6JP0QXaHR0cHM6Ly9zb3VyY2Vmb3JnZS5uZXRXA3NwY0IKZiIJ2yZmX0FUblIQdHJjZmlyQGdtYWlsLmNvbVgEAAAAAQ~~\"\n" +
                "                      style=\"text-decoration: underline;color: #666666;\" target=\"_blank\" title=\"SourceForge\">\n" +
                "                      <img alt=\"SF Logo\" class=\"image-fix\" height=\"49\"\n" +
                "                        src=\"https://raw.githubusercontent.com/huyhoang-doit/AuctionWebApp_FE/master/website/public/assets/images/favicon.ico\"\n" +
                "                        style=\"max-width: 56px;font-size: 14px;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;border: none;display: block;color: #333333\"\n" +
                "                        title=\"SF\" width=\"56\" />\n" +
                "                    </a>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </tbody>\n" +
                "    </table>\n" +
                "    <!-- End of wrapper table -->\n" +
                "\n" +
                "    <img border=\"0\" width=\"1\" height=\"1\" alt=\"\"\n" +
                "      src=\"https://l.sourceforge.net/q/3xRj8PDFMzdZv3oiy3pleQ~~/AABcDgA~/RgRoCA6JPlcDc3BjQgpmIgnbJmZfQVRuUhB0cmNmaXJAZ21haWwuY29tWAQAAAAB\">\n" +
                "  </body>\n" +
                "\n" +
                "  </html>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n" +
                "</table>\n" +
                "</div>";
        return content;
    }
}
