package vn.webapp.backend.auction.service.role;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.enums.Role;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService{
    @Override
    public List<Role> getAllRoles() {
        return List.of(Role.values());
    }

}
