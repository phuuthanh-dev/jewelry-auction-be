package vn.webapp.backend.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.model.Role;
import vn.webapp.backend.auction.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String usename);

    @Query("SELECT u FROM User u WHERE u.role.name = :role")
    List<User> findAllByRole(@Param("role") String roleName);

    @Query("SELECT u FROM User u " +
            "WHERE (:fullName IS NULL OR CONCAT(u.firstName,' ',u.lastName) LIKE %:fullName%) " +
            "AND (:roleId IS NULL OR u.role.id = :roleId) " +
            "AND (:state IS NULL OR u.state = :state)")
    Page<User> findByFullNameContainingAndRoleAndState(
            @Param("fullName") String fullName,
            @Param("roleId") Integer roleId,
            @Param("state") AccountState state,
            Pageable pageable);
    Page<User> findByFullNameContainingAndRoleNotAndState(
            String fullName,
            Integer roleId,
            AccountState state,
            Pageable pageable);
}
