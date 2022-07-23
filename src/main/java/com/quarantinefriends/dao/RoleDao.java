package com.quarantinefriends.dao;

import com.quarantinefriends.dto.RoleDTO;
import com.quarantinefriends.entity.Role;
import com.quarantinefriends.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoleDao {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleDao(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public static RoleDTO mapToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        if(role != null) {
            roleDTO.setId(role.getId());
            roleDTO.setName(role.getName());
        }
        return roleDTO;
    }

    public static Role mapToEntity(RoleDTO roleDTO) {
        Role role = new Role();
        if(roleDTO != null) {
            role.setId(roleDTO.getId());
            role.setName(roleDTO.getName());
        }
        return role;
    }

    public RoleDTO findById(Long id) {
        Optional<Role> role = this.roleRepository.findById(id);
        return role.map(RoleDao::mapToDTO).orElse(null);
    }
}
