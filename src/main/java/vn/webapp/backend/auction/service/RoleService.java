package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.model.Role;
import vn.webapp.backend.auction.repository.RoleRepository;
import vn.webapp.backend.auction.repository.UserRepository;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
