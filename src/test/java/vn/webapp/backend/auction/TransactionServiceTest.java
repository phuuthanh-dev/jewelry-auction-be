package vn.webapp.backend.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import vn.webapp.backend.auction.dto.UserTransactionResponse;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.Transaction;
import vn.webapp.backend.auction.service.TransactionService;

import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest
public class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;

    @Test
    public void testGetAllReturnsWell(){
        // Act
        List<Transaction> transactions = transactionService.getAll();

        // Assert
        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());
    }
    @Test
    public void testGetTransactionByIdReturnsWell(){
        // Expected
        Integer id = 1;

        // Act
        Transaction transaction = transactionService.getTransactionById(id);

        // Assert
        assertNotNull(transaction);
        assertEquals(transaction.getId(),id);
    }

    @Test
    public void testGetTransactionByIdReturnsNull(){
        // Expected
        Integer nonExistId = 99;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> transactionService.getTransactionById(nonExistId));
    }

//    @Test //>>>CHECK LẠI UserTransactionResponse<<<
//    public void testGetTransactionsDashboardByUsername(){
//        //Expected
//        String username = "phuuthanh2003";
//
//        //Act
//        UserTransactionResponse userTransactionResponse = transactionService.getTransactionsDashboardByUsername(username);
//
//        //Assert
//        assertNotNull(userTransactionResponse);
//        assertTrue(username, userTransactionResponse.);
//    }

//    @Test // >>>CHECK LẠI transaction.getType()<<<
//    public void testGetTransactionByType(){
//        //Expected
//        String typename = "Type đếu biết";
//
//        //Act
//        List<Transaction> transactions = transactionService.getTransactionByType(typename);
//
//        //Assert
//        assertNotNull(transactions);
//        assertFalse(transactions.isEmpty());
//
//        for (Transaction transaction : transactions){
//            assertTrue(transaction.getType().toLowerCase().contains(typename.toLowerCase()));
//        }
//    }

}
