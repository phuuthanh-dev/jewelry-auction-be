package vn.webapp.backend.auction.service.request_approval;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.CancelRequestApproval;
import vn.webapp.backend.auction.dto.ManagerRequestApproval;
import vn.webapp.backend.auction.dto.StaffRequestApproval;
import vn.webapp.backend.auction.dto.UserRequestApproval;
import vn.webapp.backend.auction.enums.JewelryState;
import vn.webapp.backend.auction.enums.RequestApprovalState;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.exception.UserNotFoundException;
import vn.webapp.backend.auction.model.ErrorMessages;
import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.model.RequestApproval;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.JewelryRepository;
import vn.webapp.backend.auction.repository.RequestApprovalRepository;
import vn.webapp.backend.auction.repository.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;

@Transactional
@Service
@RequiredArgsConstructor
public class RequestApprovalServiceImpl implements RequestApprovalService {
    private final RequestApprovalRepository requestApprovalRepository;
    private final UserRepository userRepository;
    private final JewelryRepository jewelryRepository;

    @Override
    public RequestApproval getRequestById(Integer id) {
        return requestApprovalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.REQUEST_APPROVAL_NOT_FOUND));
    }

    @Override
    public void setRequestState(Integer id, Integer responderId, String state) {
        var existingRequest = requestApprovalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.REQUEST_APPROVAL_NOT_FOUND));
        var existUser = userRepository.findById(responderId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        if (existUser.getRole().equals(Role.STAFF)) {
            existingRequest.setStaff(existUser);
        }
        if (existUser.getRole().equals(Role.MEMBER)) {
            existingRequest.getJewelry().setState(JewelryState.HIDDEN);
        }
        existingRequest.setResponder(existUser);
        existingRequest.setState(RequestApprovalState.valueOf(state));
        existingRequest.setConfirm(false);
        existingRequest.setResponseTime(Timestamp.from(Instant.now()));
    }

    @Override
    public void confirmRequest(Integer requestId, Integer responderId) {
        var request = requestApprovalRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.REQUEST_APPROVAL_NOT_FOUND));

        var responder = userRepository.findById(responderId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));

        if (responder.getRole().equals(Role.STAFF)) {
            request.setStaff(responder);
        } else if (responder.getRole().equals(Role.MEMBER)) {
            request.getJewelry().setState(JewelryState.ACTIVE);
        }

        request.setConfirm(true);
        request.setResponder(responder);
        request.setResponseTime(Timestamp.from(Instant.now()));
    }

    @Override
    public void cancelRequest(CancelRequestApproval request) {
        var existingRequest = requestApprovalRepository.findById(request.requestId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.REQUEST_APPROVAL_NOT_FOUND));
        Jewelry jewelry = existingRequest.getJewelry();
        jewelry.setState(JewelryState.HIDDEN);
        existingRequest.setJewelry(jewelry);
        existingRequest.setNote(request.note());
    }

    @Override
    public Page<RequestApproval> getRequestBySenderRole(Role role, String jewelryName, String category, Pageable pageable) {
        if (category.equals("Tất cả"))
            return requestApprovalRepository.findRequestApprovalBySenderRole(role, jewelryName, null, pageable);
        return requestApprovalRepository.findRequestApprovalBySenderRole(role, jewelryName, category, pageable);
    }

    @Override
    public RequestApproval requestFromUser(UserRequestApproval request) {
        User sender = userRepository.findById(request.senderId())
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        Jewelry jewelry = jewelryRepository.findById(request.jewelryId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.JEWELRY_NOT_FOUND));

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
        User sender = userRepository.findById(request.senderId())
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        RequestApproval oldRequest = requestApprovalRepository.findById(request.requestApprovalId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.REQUEST_APPROVAL_NOT_FOUND));

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
        User sender = userRepository.findById(request.senderId())
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        RequestApproval oldRequest = requestApprovalRepository.findById(request.requestApprovalId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.REQUEST_APPROVAL_NOT_FOUND));

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
    public Page<RequestApproval> getRequestApprovalByUserId(Integer id, String jewelryName, Pageable pageable) {
        return requestApprovalRepository.findRequestApprovalByUserId(id, jewelryName, pageable);
    }

    @Override
    public Page<RequestApproval> getRequestApprovalPassed(String jewelryName, String category, Pageable pageable) {
        if (category.equals("Tất cả")) {
            return requestApprovalRepository.findRequestApprovalPassed(jewelryName, null, pageable);
        }
        return requestApprovalRepository.findRequestApprovalPassed(jewelryName, category, pageable);
    }

    @Override
    public Page<RequestApproval> getRequestNeedConfirmByMember(Integer memberId, Pageable pageable) {
        return requestApprovalRepository.findRequestNeedConfirmByMember(memberId, pageable);
    }
}
