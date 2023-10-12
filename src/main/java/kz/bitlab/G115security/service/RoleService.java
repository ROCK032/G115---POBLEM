package kz.bitlab.G115security.service;

import kz.bitlab.G115security.entity.Role;
import kz.bitlab.G115security.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public Role getRoleUser(){
        return roleRepository.findRoleUser();
    }
}
