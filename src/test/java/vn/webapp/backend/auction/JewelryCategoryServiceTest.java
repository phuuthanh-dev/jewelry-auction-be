package vn.webapp.backend.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.JewelryCategory;
import vn.webapp.backend.auction.service.JewelryCategoryService;

import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest
public class JewelryCategoryServiceTest extends AbstractTestNGSpringContextTests{
    @Autowired
    private JewelryCategoryService jewelryCategoryService;

    @Test
    public void testGetAllReturnsWell(){
        //Act
        List<JewelryCategory> jewelries = jewelryCategoryService.getAll();

        //Assert
        assertNotNull(jewelries);
        assertFalse(jewelries.isEmpty());
    }

    @Test
    public void testGetJewelryCategoryByIdReturnsWell(){
        // Expected
        Integer id = 1;

        // Act
        JewelryCategory jewelryCategory = jewelryCategoryService.getById(id);

        // Assert
        assertNotNull(jewelryCategory);
        assertEquals(jewelryCategory.getId(), id);
    }

    @Test
    public void testGetJewelryCategoryByIdReturnNull(){
        // Expected
        Integer nonExistId = 99;

        //Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> jewelryCategoryService.getById(nonExistId));
    }

//    @Test
//    public void testSaveJewelryCategory(){
//        // Expected
//        JewelryCategory jewelryCategoryToSave = JewelryCategory.builder().name("Dây chuyền").build();
//        JewelryCategory savedJewelryCategory = JewelryCategory.builder()
//                .id(1)
//                .name("Dây chuyền")
//                .build();
//        //Act
//        JewelryCategory result = jewelryCategoryService.saveJewelryCategory(jewelryCategoryToSave);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(savedJewelryCategory.getId(), result.getId());
//        assertEquals(savedJewelryCategory.getName(), result.getName());
//
////        verify(jewelryCategoryRepository, times(1)).save(any(JewelryCategory.class));
//    }
}
