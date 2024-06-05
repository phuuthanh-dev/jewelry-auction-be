package vn.webapp.backend.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.model.RequestApproval;

import java.util.List;

public interface RequestApprovalRepository extends JpaRepository<RequestApproval, Integer> {
    @Query("SELECT ra FROM RequestApproval ra WHERE ra.sender.role = :role AND ra.isConfirm = false AND ra.state = 'ACTIVE'")
    Page<RequestApproval> findRequestApprovalBySenderRole(@Param("role") Role role, Pageable pageable);
}
