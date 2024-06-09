package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.ManagerRequestApproval;
import vn.webapp.backend.auction.dto.StaffRequestApproval;
import vn.webapp.backend.auction.dto.UserRequestApproval;
import vn.webapp.backend.auction.enums.RequestApprovalState;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.model.RequestApproval;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.JewelryRepository;
import vn.webapp.backend.auction.repository.RequestApprovalRepository;
import vn.webapp.backend.auction.repository.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class RequestApporvalServiceImpl implements RequestApprovalService{
    private final RequestApprovalRepository requestApprovalRepository;
    private final UserRepository userRepository;
    private final JewelryRepository jewelryRepository;

    @Override
    public RequestApproval getRequestById(Integer id) {
        return requestApprovalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yêu cầu không tồn tại"));
    }

    @Override
    public void setRequestState(Integer id, String state) {
        var existingRequest = requestApprovalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy yêu cầu này."));
        existingRequest.setState(RequestApprovalState.valueOf(state));
        existingRequest.setConfirm(false);
        existingRequest.setResponseTime(Timestamp.from(Instant.now()));
    }

    @Override
    public void confirmRequest(Integer id, Integer responderId) {
        var existingRequest = requestApprovalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy yêu cầu này."));
        var existUser = userRepository.findById(responderId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng này."));
        if(existUser.getRole().equals(Role.STAFF)) {
            existingRequest.setStaff(existUser);
        }
        existingRequest.setConfirm(true);
        existingRequest.setResponder(existUser);
        existingRequest.setResponseTime(Timestamp.from(Instant.now()));
    }

    @Override
    public Page<RequestApproval> getRequestBySenderRole(Role role, Pageable pageable) {
        return requestApprovalRepository.findRequestApprovalBySenderRole(role, pageable);
    }

    @Override
    public RequestApproval requestFromUser(UserRequestApproval request) {
        Optional<User> existSender = userRepository.findById(request.senderId());
        if (existSender.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + request.senderId() + " not found");
        }

        Optional<Jewelry> existJewelry = jewelryRepository.findById(request.jewelryId());
        if (existJewelry.isEmpty()) {
            throw new IllegalArgumentException("Jewelry with ID " + request.jewelryId() + " not found");
        }
        User sender = existSender.get();
        Jewelry jewelry = existJewelry.get();
        RequestApproval newRequest = new RequestApproval();
        newRequest.setRequestTime(request.requestTime());
        newRequest.setJewelry(jewelry);
        newRequest.setConfirm(false);
        newRequest.setState(RequestApprovalState.ACTIVE);
        newRequest.setSender(sender);
        newRequest.setDesiredPrice(jewelry.getPrice());
        requestApprovalRepository.save(newRequest);
        return newRequest;
    }

    @Override
    public RequestApproval requestFromStaff(StaffRequestApproval request) {
        Optional<User> existSender = userRepository.findById(request.senderId());
        if (existSender.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + request.senderId() + " not found");
        }

        Optional<RequestApproval> existRequestApproval = requestApprovalRepository.findById(request.requestApprovalId());
        if (existRequestApproval.isEmpty()) {
            throw new IllegalArgumentException("Request with ID " + request.requestApprovalId() + " not found");
        }
        User sender = existSender.get();
        RequestApproval oldRequest = existRequestApproval.get();
        RequestApproval newRequest = new RequestApproval();
        newRequest.setRequestTime(request.requestTime());
        newRequest.setJewelry(oldRequest.getJewelry());
        newRequest.setConfirm(false);
        newRequest.setState(RequestApprovalState.ACTIVE);
        newRequest.setSender(sender);
        newRequest.setDesiredPrice(oldRequest.getJewelry().getPrice());
        newRequest.setValuation(request.valuation());
        newRequest.setStaff(sender);
        requestApprovalRepository.save(newRequest);
        return newRequest;
    }

    @Override
    public RequestApproval requestFromManager(ManagerRequestApproval request) {
        Optional<User> existSender = userRepository.findById(request.senderId());
        if (existSender.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + request.senderId() + " not found");
        }

        Optional<RequestApproval> existRequestApproval = requestApprovalRepository.findById(request.requestApprovalId());
        if (existRequestApproval.isEmpty()) {
            throw new IllegalArgumentException("Request with ID " + request.requestApprovalId() + " not found");
        }
        User sender = existSender.get();
        RequestApproval oldRequest = existRequestApproval.get();
        RequestApproval newRequest = new RequestApproval();

        newRequest.setRequestTime(request.requestTime());
        newRequest.setJewelry(oldRequest.getJewelry());
        newRequest.setConfirm(false);
        newRequest.setState(RequestApprovalState.ACTIVE);
        newRequest.setSender(sender);
        newRequest.setDesiredPrice(oldRequest.getDesiredPrice());
        newRequest.setValuation(oldRequest.getValuation());
        newRequest.setStaff(oldRequest.getSender());
        requestApprovalRepository.save(newRequest);
        return newRequest;
    }

    @Override
    public Page<RequestApproval> getRequestApprovalByUserId(Integer id, Pageable pageable) {
        return requestApprovalRepository.findRequestApprovalByUserId(id, pageable);
    }
}
