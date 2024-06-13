package vn.webapp.backend.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.RequestApproval;
import vn.webapp.backend.auction.service.RequestApprovalService;

import static org.testng.Assert.*;

@SpringBootTest
public class RequestApprovalServiceTest {

    @Autowired
    private RequestApprovalService requestApprovalService;

    @Test
    public void testGetRequestByIdReturnsWell(){
        // Expected
        Integer id = 1;

        // Act
        RequestApproval requestApproval = requestApprovalService.getRequestById(id);

        //Assert
        assertNotNull(requestApproval);
        assertEquals(requestApproval.getId(), id);
    }

    @Test
    public void testGetRequestByIdReturnsNull(){
        // Expected
        Integer nonExistId = 99;

        //Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> requestApprovalService.getRequestById(nonExistId));
    }
}
