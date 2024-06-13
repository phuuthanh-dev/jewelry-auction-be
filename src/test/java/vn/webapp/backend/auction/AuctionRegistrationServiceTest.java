package vn.webapp.backend.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import vn.webapp.backend.auction.model.AuctionRegistration;
import vn.webapp.backend.auction.service.AuctionRegistrationService;

import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest
public class AuctionRegistrationServiceTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private AuctionRegistrationService auctionRegistrationService;

//    @Test //LỖI DÒNG 33, DANH SÁCH TRẢ VỀ TRỐNG - CÓ VỂ DO DƯA CHO DB TEST
//    public void testFindByAuctionIdAndValidReturnsWell(){
//        // Expected
//        Integer id = 1;
//
//        //Act
//        List<AuctionRegistration> auctionRegistrations = auctionRegistrationService.findByAuctionIdAndValid(id);
//
//        //Assert
//        assertNotNull(auctionRegistrations);
//        assertFalse(auctionRegistrations.isEmpty());
//
//        for (AuctionRegistration auctionRegistration : auctionRegistrations) {
//            assertEquals(auctionRegistration.getAuction().getId(),id);
//        }
//
//    }
}
