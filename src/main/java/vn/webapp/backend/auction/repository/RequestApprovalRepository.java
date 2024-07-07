package vn.webapp.backend.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.model.RequestApproval;


public interface RequestApprovalRepository extends JpaRepository<RequestApproval, Integer> {
    @Query("SELECT ra FROM RequestApproval ra WHERE ra.sender.role = :role " +
            "AND (:jewelryName IS NULL OR ra.jewelry.name LIKE %:jewelryName%) " +
            "AND (:category IS NULL OR :category = 'Tất cả' OR ra.jewelry.category.name = :category) " +
            "AND ra.isConfirm = false AND ra.state = 'ACTIVE'")
    Page<RequestApproval> findRequestApprovalBySenderRole(
            @Param("role") Role role,
            @Param("jewelryName") String jewelryName,
            @Param("category") String category,
            Pageable pageable
    );

    @Query("SELECT ra FROM RequestApproval ra WHERE ra.sender.role = 'MANAGER' AND ra.isConfirm = false AND ra.jewelry.user.id = :memberId AND ra.state = 'ACTIVE'")
    Page<RequestApproval> findRequestNeedConfirmByMember(@Param("memberId") Integer memberId, Pageable pageable);

    @Query("SELECT ra FROM RequestApproval ra WHERE ra.sender.id = :id AND (:jewelryName IS NULL OR ra.jewelry.name LIKE %:jewelryName%)")
    Page<RequestApproval> findRequestApprovalByUserId(@Param("id") Integer id, @Param("jewelryName") String jewelryName,Pageable pageable);

    @Query("SELECT ra FROM RequestApproval ra WHERE ra.sender.role = 'MANAGER' " +
            "AND (:jewelryName IS NULL OR ra.jewelry.name LIKE %:jewelryName%) " +
            "AND (:category IS NULL OR :category = 'Tất cả' OR ra.jewelry.category.name = :category) " +
            "AND ra.isConfirm = true AND ra.state = 'ACTIVE' " +
            "AND ra.jewelry.state = 'ACTIVE' AND ra.jewelry.isHolding = true")
    Page<RequestApproval> findRequestApprovalPassed(
            @Param("jewelryName") String jewelryName,
            @Param("category") String category,
            Pageable pageable
    );

}
