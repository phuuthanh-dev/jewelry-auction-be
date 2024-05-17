package vn.webapp.backend.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.webapp.backend.auction.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
