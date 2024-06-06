package vn.webapp.backend.auction.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.webapp.backend.auction.dto.UserRequestApproval;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.RequestApproval;

public interface RequestApprovalService {
    RequestApproval getRequestById(Integer id);
    void setRequestState(Integer id, String state);
    Page<RequestApproval> getRequestBySenderRole(Role role, Pageable pageable);
    RequestApproval requestFromUser(UserRequestApproval request);
    Page<RequestApproval> getRequestApprovalByUserId(Integer id, Pageable pageable);
}
