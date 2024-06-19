package vn.webapp.backend.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.model.RequestApproval;


public interface RequestApprovalRepository extends JpaRepository<RequestApproval, Integer> {
    @Query("SELECT ra FROM RequestApproval ra WHERE ra.sender.role = :role AND ra.isConfirm = false AND ra.state = 'ACTIVE'")
    Page<RequestApproval> findRequestApprovalBySenderRole(@Param("role") Role role, Pageable pageable);

    @Query("SELECT ra FROM RequestApproval ra WHERE ra.sender.role = 'MANAGER' AND ra.isConfirm = false AND ra.jewelry.user.id = :memberId AND ra.state = 'ACTIVE'")
    Page<RequestApproval> findRequestNeedConfirmByMember(@Param("memberId") Integer memberId, Pageable pageable);

    @Query("SELECT ra FROM RequestApproval ra WHERE ra.sender.id = :id")
    Page<RequestApproval> findRequestApprovalByUserId(@Param("id") Integer id, Pageable pageable);

    @Query("SELECT ra FROM RequestApproval ra WHERE ra.sender.role = 'MANAGER' AND ra.isConfirm = true AND ra.state = 'ACTIVE' AND ra.jewelry.state = 'APPROVING'")
    Page<RequestApproval> findRequestApprovalPassed( Pageable pageable);

}
