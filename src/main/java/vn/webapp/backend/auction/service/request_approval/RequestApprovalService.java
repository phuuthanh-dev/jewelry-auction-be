package vn.webapp.backend.auction.service.request_approval;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.webapp.backend.auction.dto.CancelRequestApproval;
import vn.webapp.backend.auction.dto.ManagerRequestApproval;
import vn.webapp.backend.auction.dto.StaffRequestApproval;
import vn.webapp.backend.auction.dto.UserRequestApproval;
import vn.webapp.backend.auction.enums.JewelryState;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.model.RequestApproval;

public interface RequestApprovalService {
    RequestApproval getRequestById(Integer id);
    void setRequestState(Integer id, Integer responderId, String state);
    void confirmRequest(Integer id, Integer responderId);
    void cancelRequest(CancelRequestApproval request);
    Page<RequestApproval> getRequestBySenderRole(Role role, String jewelryName, String category, Pageable pageable);
    RequestApproval requestFromUser(UserRequestApproval request);
    RequestApproval requestFromStaff(StaffRequestApproval request);
    RequestApproval requestFromManager(ManagerRequestApproval request);
    Page<RequestApproval> getRequestApprovalByUserId(Integer id, String jewelryName,Pageable pageable);
    Page<RequestApproval> getRequestApprovalPassed(String jewelryName, String category, Pageable pageable);
    Page<RequestApproval> getRequestNeedConfirmByMember(Integer memberId, Pageable pageable);
}
