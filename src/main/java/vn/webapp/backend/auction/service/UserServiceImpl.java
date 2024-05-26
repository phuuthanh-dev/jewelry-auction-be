package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.RegisterAccountRequest;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.exception.UserAlreadyExistsException;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.UserRepository;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
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
            return userRepository.findAllByRole(Role.STAFF);
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

    @Override
    public User updateUser(User user) {
        var existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user tương ứng"));
        existingUser.setAddress(user.getAddress());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setProvince(user.getProvince());
        existingUser.setCity(user.getCity());
        existingUser.setAvatar(user.getAvatar());
        existingUser.setPhone(user.getPhone());
        existingUser.setYob(user.getYob());
        existingUser.setBankAccountNumber((user.getBankAccountNumber()));
        return existingUser;
    }

    @Override
    public Page<User> getMemberByFullNameContainingAndState(
            String fullName,
            AccountState state,
            Pageable pageable) {
        return userRepository.findByFullNameContainingAndRoleAndState(fullName, Role.MEMBER, state, pageable);
    }

    @Override
    public Page<User> getStaffByFullNameContainingAndRoleAndState(
            String fullName,
            Role role,
            AccountState state,
            Pageable page) {
        if (role == null)
            return userRepository.findByFullNameContainingAndRoleAndState(fullName, Role.MEMBER, state, page);

        return userRepository.findByFullNameContainingAndRoleAndState(fullName, role, state, page);
    }

    @Override
    public User registerStaff(RegisterAccountRequest request) {
        userRepository.findByUsername(request.username())
                .ifPresent(existingUser -> {
                    throw new UserAlreadyExistsException("Người dùng đã tồn tại.");
                });
        userRepository.findByEmail(request.email())
                .ifPresent(existingUser -> {
                    throw new UserAlreadyExistsException("Người dùng đã tồn tại.");
                });
        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .state(AccountState.ACTIVE)
                .province(request.province())
                .city(request.city())
                .yob(request.yob())
                .phone(request.phone())
                .address(request.address())
                .CCCD(request.CCCD())
                .build();
        return userRepository.save(user);
    }
}