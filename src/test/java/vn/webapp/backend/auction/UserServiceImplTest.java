package vn.webapp.backend.auction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.UserRepository;
import vn.webapp.backend.auction.service.UserServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceImplTest  {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void PokemonService_FindById_ReturnPokemonDto() {
        String username = "phuuthanh20031";
        User user = User.builder().id(113).username("phuuthanh2003").build();
        given(userRepository.findByUsername(username))
                .willReturn(Optional.of(user));
        var  personList = userService.getUserByUsername(username);
        System.out.println(personList);
        Assertions.assertThat(personList).isNotNull();
    }

//    @Test
//    public void testGetUserByUsernameWrongThrowException() {
//
//        // Assert
//        assertThrows(ResourceNotFoundException.class, () -> {
//            userService.getUserByUsername("phuuthanh20033");
//        });
//    }
}
