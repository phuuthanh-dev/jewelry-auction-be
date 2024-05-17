package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng tương ứng không tồn tại"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tai khoan."));
    }

//    @Override
//    public User updateUser(User user) {
//        var existingUser = userRepository.findById(user.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Khong tim thay user tuong ung"));
//        existingUser.setAddress(user.getAddress());
//        existingUser.setFullName(user.getFullName());
//        existingUser.setProvince(user.getProvince());
//        existingUser.setDistrict(user.getDistrict());
//        existingUser.setWard(user.getWard());
//        existingUser.setPhone(user.getPhone());
//        existingUser.setAddress(user.getAddress());
//        return existingUser;
//    }
//
//    @Override
//    public User registerStaff(RegisterRequest request) {
//        userRepository.findByEmail(request.email())
//                .ifPresent(existingUser -> {
//                    throw new UserAlreadyExistsException("Người dùng đã tồn tại.");
//                });
//        var user = User.builder()
//                .fullName(request.fullName())
//                .email(request.email())
//                .password(passwordEncoder.encode(request.password()))
//                .role(request.role())
//                .state(AccountState.ACTIVE)
//                .province(request.province())
//                .district(request.district())
//                .ward(request.ward())
//                .phone(request.phone())
//                .address(request.address())
//                .build();
//        return userRepository.save(user);
//    }
//
//    @Override
//    public void setAccountState(Long userId, String state) {
//        var existingUser = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tai khoan."));
//        existingUser.setState(AccountState.valueOf(state));
//    }
//
//    @Override
//    public void setRole(Long userId, String role) {
//        var existingUser = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tai khoan."));
//        existingUser.setRole(Role.valueOf(role));
//    }
//
//    @Override
//    public Page<User> getCustomerByFullNameContainingAndState(
//            String fullName,
//            AccountState state,
//            Pageable pageable) {
//        return userRepository.findByFullNameContainingAndRoleAndState(fullName, Role.USER, state, pageable);
//    }
//
    @Override
    public List<User> getAllStaff() {
            return userRepository.findAllByRole("STAFF");
    }



    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
