package vn.webapp.backend.auction.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.webapp.backend.auction.dto.ManagerRequestApproval;
import vn.webapp.backend.auction.dto.StaffRequestApproval;
import vn.webapp.backend.auction.dto.UserRequestApproval;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.RequestApproval;

public interface RequestApprovalService {
    RequestApproval getRequestById(Integer id);
    void setRequestState(Integer id, String state);
    void confirmRequest(Integer id, Integer responderId);
    Page<RequestApproval> getRequestBySenderRole(Role role, Pageable pageable);
    RequestApproval requestFromUser(UserRequestApproval request);
    RequestApproval requestFromStaff(StaffRequestApproval request);
    RequestApproval requestFromManager(ManagerRequestApproval request);
    Page<RequestApproval> getRequestApprovalByUserId(Integer id, Pageable pageable);
}
