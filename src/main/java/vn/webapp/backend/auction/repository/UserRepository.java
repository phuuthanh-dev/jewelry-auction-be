package vn.webapp.backend.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.webapp.backend.auction.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
