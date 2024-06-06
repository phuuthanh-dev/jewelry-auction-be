package vn.webapp.backend.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.service.AuctionService;


import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest
public class AuctionServiceTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private AuctionService auctionService;

    @Test
    public void testGetAllReturnWell(){

    }

    @Test
    public void testGetAuctionByIdReturnWell(){
        // Expected
        Integer id = 1;

        // Act
        Auction auction = auctionService.getAuctionById(id);

        // Assert
        assertNotNull(auction);
        assertEquals(auction.getId(), id);
    }

    @Test
    public void testFindAuctionByNameReturnWell(){
        // Expected
        String name = "nhẫn";
        // Act
        List<Auction> auctions = auctionService.findAuctionByName(name);
        // Assert
        assertNotNull(auctions);
        assertFalse(auctions.isEmpty());
        // Verify that each returned Jewelry item belongs to the expected username
        for (Auction auction : auctions) {
            assertTrue(auction.getName().toLowerCase().contains(name.toLowerCase()));
        }
    }
}
