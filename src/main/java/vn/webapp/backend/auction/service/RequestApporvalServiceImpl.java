package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.enums.RequestApprovalState;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.RequestApproval;
import vn.webapp.backend.auction.repository.RequestApprovalRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class RequestApporvalServiceImpl implements RequestApprovalService{
    private final RequestApprovalRepository requestApprovalRepository;

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
    }

    @Override
    public Page<RequestApproval> getRequestBySenderRole(Role role, Pageable pageable) {
        return requestApprovalRepository.findRequestApprovalBySenderRole(role, pageable);
    }
}
