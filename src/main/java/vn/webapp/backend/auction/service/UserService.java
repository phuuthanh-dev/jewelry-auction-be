package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.UserRepository;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
//    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng tương ứng không tồn tại"));
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng tương ứng không tồn tại"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản."));
    }

    @Override
    public List<User> getAllStaff() {
            return userRepository.findAllByRole("STAFF");
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void setAccountState(Integer id, String state) {
        var existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản."));
        existingUser.setState(AccountState.valueOf(state));
    }
}
