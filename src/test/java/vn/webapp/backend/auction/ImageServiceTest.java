package vn.webapp.backend.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import vn.webapp.backend.auction.model.Image;
import vn.webapp.backend.auction.service.ImageService;

import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest
public class ImageServiceTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private ImageService imageService;

    @Test
    void testGetImagesByJewelryIdReturnsWell(){
        // Expected
        Integer id = 1;

        //Act
        List<Image> images = imageService.getImagesByJewelryId(id);

        //Assert
        assertNotNull(images);
        assertFalse(images.isEmpty());

        for (Image image : images) {
            assertEquals(image.getJewelry().getId(), id);
        }
    }

    @Test
    void testGetImagesByJewelryIdReturnsNull(){
        // Expected
        Integer nonExistId = 99;

        // Act
        List<Image> images = imageService.getImagesByJewelryId(nonExistId);

        //Assert
        assertNotNull(images);
        assertTrue(images.isEmpty());
    }

    @Test
    void testGetImageByIconAndJewelryIdReturnsWell(){
        // Expected
        Integer id = 1;

        // Act
        Image image = imageService.getImageByIconAndJewelryId(id);

        // Assert
        assertNotNull(image);
        assertEquals(image.getId(), id);
    }

//    @Test CHƯA LÀM TRẢ VỀ
//    public void testGetImageByIconAndJewelryIdReturnsNull(){
//        // Expected
//        Integer nonExistId = 99;
//
//        // Act and Assert
//        assertThrows(ResourceNotFoundException.class, () -> imageService.getImageByIconAndJewelryId(nonExistId));
//
//    }
}
