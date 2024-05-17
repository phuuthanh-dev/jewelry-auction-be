package vn.webapp.backend.auction.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.model.Role;
import vn.webapp.backend.auction.model.User;

import java.util.List;

public interface IUserService {
    public User getUserById(Integer id);
    public User getUserByEmail(String email);
    List<User> getAll();
    List<User> getAllStaff();
}
