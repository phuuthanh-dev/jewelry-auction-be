package vn.webapp.backend.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;

import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.service.JewelryService;

import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest
@ActiveProfiles("test")
public class JewelryServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private JewelryService jewelryService;

    @Test
    public void testGetJewelryByIdReturnsWell(){
        // Expected
        Integer id = 1;

        // Act
        Jewelry jewelry = jewelryService.getJewelryById(id);

        // Assert
        assertNotNull(jewelry);
        assertEquals(jewelry.getId(), id);
    }

    @Test
    public void testGetJewelriesByUsernameReturnsWell(){
        // Expected
        String username = "phuuthanh2003";
        // Act
        List<Jewelry> jewelries = jewelryService.getJewelryByUsername(username);
        // Assert
        assertNotNull(jewelries);
        assertFalse(jewelries.isEmpty());
        // Verify that each returned Jewelry item belongs to the expected username
        for (Jewelry jewelry : jewelries) {
            assertEquals(jewelry.getUser().getUsername(), username);
        }
    }

    @Test
    public void TestGetJeweriesByCategoryIdReturnsWell(){
        // Expected
        Integer id = 1;
        // Act
        List<Jewelry> jewelries = jewelryService.getJeweriesByCategoryId(id);
        // Assert
        assertNotNull(jewelries);
        assertFalse(jewelries.isEmpty());
        // Verify that each returned Jewelry item belongs to the expected username
        for (Jewelry jewelry : jewelries) {
            assertEquals(jewelry.getCategory().getId(), id);
        }
    }

    @Test
    public void testGetJewelriesByNameContainReturnsWell(){
        // Expected
        String key = "Nhẫn";
        // Act
        List<Jewelry> jewelries = jewelryService.getJeweriesByNameContain(key);
        // Assert
        assertNotNull(jewelries);
        assertFalse(jewelries.isEmpty());
        // Verify that each returned Jewelry item belongs to the expected username
        for (Jewelry jewelry : jewelries) {
            assertTrue(jewelry.getDescription().toLowerCase().contains(key.toLowerCase()));
        }
    }

    @Test
    public void testGetJewelryByIdReturnsNull(){
        Integer nonExistId = 99;

        assertThrows(ResourceNotFoundException.class, () -> jewelryService.getJewelryById(nonExistId));
    }

//    @Test
//    public void testGetJewelriesByUsernameReturnNull(){
//        String nonExistUsername = "tam12345";
//        assertThrows(ResourceNotFoundException.class, () -> jewelryService.getJewelriesByUsername(nonExistUsername));
////    }
//    @Test
//    public void TestGetJeweriesByCategoryIdReturnsNulll(){
//
//    }
//    @Test
//    public void testGetJewelriesByNameContainReturnsNull(){
//
//    }

}

