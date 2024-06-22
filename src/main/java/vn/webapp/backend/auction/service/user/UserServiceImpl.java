package vn.webapp.backend.auction.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.RegisterAccountRequest;
import vn.webapp.backend.auction.dto.UserSpentDTO;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.exception.UserAlreadyExistsException;
import vn.webapp.backend.auction.model.ErrorMessages;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.AuctionRegistrationRepository;
import vn.webapp.backend.auction.repository.TransactionRepository;
import vn.webapp.backend.auction.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuctionRegistrationRepository auctionRegistrationRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }

    @Override
    public User findUserByUsernameOrEmail(String username) {
        return userRepository.findByUsername(username)
                .orElseGet(() -> userRepository.findByEmail(username)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Người dùng với username hoặc email: " + username + " không tồn tại. Vui lòng đăng ký tài khoản mới.")));
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
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
    public void setAccountState(Integer id, AccountState state) {
        var existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        existingUser.setState(state);
    }

    @Override
    public User updateUser(User user) {
        var existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));

        existingUser.setAddress(user.getAddress());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setDistrict(user.getDistrict());
        existingUser.setWard(user.getWard());
        existingUser.setCity(user.getCity());
        existingUser.setAvatar(user.getAvatar());
        existingUser.setCccd(user.getCccd());
        existingUser.setCccdFirst(user.getCccdFirst());
        existingUser.setCccdLast(user.getCccdLast());
        existingUser.setCccdFrom(user.getCccdFrom());
        existingUser.setPhone(user.getPhone());
        existingUser.setYob(user.getYob());
        existingUser.setBankAccountNumber(user.getBankAccountNumber());
        existingUser.setBankAccountName(user.getBankAccountName());
        existingUser.setBank(user.getBank());
        return existingUser;
    }

    @Override
    public Page<User> getUsersUnVerifyByFullNameContainingAndState(
            String fullName,
            AccountState state,
            Pageable pageable) {
        return userRepository.findByFullNameContainingAndStateNot(fullName, state, pageable);
    }

    @Override
    public void rejectVerifyUser(Integer id) {
        var existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        existingUser.setCccdFirst(null);
        existingUser.setCccdLast(null);
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

        // Default to MEMBER if role is null
        if (role == null) {
            role = Role.MEMBER;
        }

        // Perform the query based on role
        switch (role) {
            case MEMBER:
                return userRepository.findByFullNameContainingAndRoleAndState(fullName, Role.MEMBER, state, page);
            case MANAGER:
                return userRepository.findByFullNameContainingAndRoleAndState(fullName, Role.MANAGER, state, page);
            case STAFF:
                return userRepository.findByFullNameContainingAndRoleAndState(fullName, Role.STAFF, state, page);
            default:
                // Handle any other roles or default case
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }


    @Override
    public User getLatestUserInAuctionHistoryByAuctionId(Integer auctionId) {
        return userRepository.findLatestUserInAuctionHistoryByAuctionId(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }

    @Override
    public User registerStaff(RegisterAccountRequest request) {
        userRepository.findByUsername(request.username())
                .ifPresent(existingUser -> {
                    throw new UserAlreadyExistsException(ErrorMessages.USER_ALREADY_EXIST);
                });
        userRepository.findByEmail(request.email())
                .ifPresent(existingUser -> {
                    throw new UserAlreadyExistsException(ErrorMessages.USER_ALREADY_EXIST);
                });
        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .state(AccountState.ACTIVE)
                .district(request.district())
                .ward(request.ward())
                .city(request.city())
                .yob(request.yob())
                .registerDate(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))))
                .phone(request.phone())
                .address(request.address())
                .cccd(request.cccd())
                .cccdFirst(request.cccdFirst())
                .cccdLast(request.cccdLast())
                .cccdFrom(request.cccdFrom())
                .build();
        return userRepository.save(user);
    }

    @Override
    public List<UserSpentDTO> getMostSpentUser() {
        List<User> users = userRepository.findTopUsersByTotalSpent();
        List<UserSpentDTO> list = new ArrayList<>();
        for (User user : users) {
            Double totalSpent = transactionRepository.getTotalPriceJewelryWonByUsernameAndAlreadyPay(user.getUsername());
            list.add(new UserSpentDTO(user, totalSpent));
        }
        return list;
    }

}
