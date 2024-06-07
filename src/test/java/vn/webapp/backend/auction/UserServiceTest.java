package vn.webapp.backend.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.service.UserService;

import static org.testng.Assert.*;



@SpringBootTest
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserService userService;

    @Test
    public void testGetUserByUsernameReturnsWell() {
        // Expected
        String username = "phuuthanh2003";
        // Act
        User user = userService.getUserByUsername(username);
        // Assert
        assertNotNull(user);
        assertEquals(user.getUsername(), username);
    }

    @Test
    public void testGetUserByIdReturnsWell() {
        // Expected
        Integer id = 1;

        // Act
        User user = userService.getUserById(id);

        // Assert
        assertNotNull(user);
        assertEquals(user.getId(), id);
    }

    @Test
    public void testGetUserByEmailReturnsWell() {
        // Expected
        String email = "phuuthanh2003@gmail.com";
        // Act
        User user = userService.getUserByEmail(email);
        // Assert
        assertNotNull(user);
        assertEquals(user.getEmail(), email);
    }

    @Test
    public void testGetUserByIdReturnsNull() {
        Integer nonExistId = 99;

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(nonExistId));
    }

    @Test
    public void testGetUserByUsernameReturnsNull() {
        String nonExistUsername = "null";

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByUsername(nonExistUsername));
    }

    @Test
    public void testGetUserByEmailReturnsNull() {
        String nonExistEmail = "null";

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByEmail(nonExistEmail));
    }

}

