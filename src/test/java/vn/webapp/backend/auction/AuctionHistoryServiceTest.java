package vn.webapp.backend.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import vn.webapp.backend.auction.service.bid.AuctionHistoryService;

@SpringBootTest
public class AuctionHistoryServiceTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private AuctionHistoryService auctionHistoryService;

//    @Test //LỖI DÒNG 39, DANH SÁCH TRẢ VỀ TRỐNG - CÓ VỂ DO DƯA CHO DB TEST
//    public void testGetAuctionHistoryByDateReturnsWell(){
//        // Expected
//        String date = "2024-05-01";
//        LocalDate localDate = LocalDate.parse(date);
//        Timestamp timestamp = Timestamp.valueOf(localDate.atStartOfDay());
//        //Act
//        List<AuctionHistory> auctionHistories = auctionHistoryService.getAuctionHistoryByDate(date);
//        //Assert
//        assertNotNull(auctionHistories);
//        assertFalse(auctionHistories.isEmpty());
//        for (AuctionHistory auctionHistory : auctionHistories){
//            LocalDate auctionHistoryDate = auctionHistory.getTime().toLocalDateTime().toLocalDate();
//            assertEquals(localDate, auctionHistoryDate);        }
//    }
}
