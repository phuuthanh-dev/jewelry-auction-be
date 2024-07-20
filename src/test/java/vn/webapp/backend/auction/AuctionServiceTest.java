package vn.webapp.backend.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import vn.webapp.backend.auction.enums.AuctionState;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.service.auction.AuctionService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest
@ActiveProfiles("test")
public class AuctionServiceTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private AuctionService auctionService;

    @Test
    public void testGetAllReturnsWell(){
        // Act
        List<Auction> auctions = auctionService.getAll();

        // Assert
        assertNotNull(auctions);
        assertFalse(auctions.isEmpty());
    }

    @Test
    public void testGetAuctionByIdReturnsWell(){
        // Expected
        Integer id = 1;

        // Act
        Auction auction = auctionService.getAuctionById(id);

        // Assert
        assertNotNull(auction);
        assertEquals(auction.getId(), id);
    }

    @Test
    public void testFindAuctionByNameReturnsWell(){
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

    @Test
    public void testGetAuctionByStateReturnsWell() {
        // Expected
        AuctionState state = AuctionState.ONGOING;
        // Act
        List<Auction> auctions = auctionService.getAuctionByState(state);
        // Assert
        assertNotNull(auctions);
        assertFalse(auctions.isEmpty());
        for (Auction auction : auctions) {
            assertEquals(state, auction.getState());
        }
    }

    @Test
    public void testGetCurrentAuctionByJewelryIdReturnsWell() {
        // Expected
        Integer jewelryId = 1;
        // Act
        Auction auction = auctionService.getCurrentAuctionByJewelryId(jewelryId);
        // Assert
        assertNotNull(auction);
        assertEquals(auction.getJewelry().getId(), jewelryId);
    }
    // ===================================================================
    @Test
    public void testGetAuctionByIdReturnsNull() {
        // Expected
        Integer nonExistId = 99;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> auctionService.getAuctionById(nonExistId));
    }

    @Test
    public void testFindAuctionByNameReturnsNullWhenNoAuctionsFound() {
        // Expected
        String nonExistentName = "nonexistentname";

        // Act
        List<Auction> auctions = auctionService.findAuctionByName(nonExistentName);

        // Assert
        assertNotNull(auctions);
        assertTrue(auctions.isEmpty());
    }

    @Test
    public void testGetAuctionByStateReturnsEmptyList() {
        // Expected
        AuctionState state = AuctionState.DELETED;

        // Act
        List<Auction> auctions = auctionService.getAuctionByState(state);

        // Assert
        assertNotNull(auctions);
        assertTrue(auctions.isEmpty());
    }
}
