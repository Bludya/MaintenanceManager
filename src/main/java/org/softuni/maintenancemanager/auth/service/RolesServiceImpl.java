package org.softuni.maintenancemanager.auth.service;

import org.softuni.maintenancemanager.appUtils.CharacterEscapes;
import org.softuni.maintenancemanager.auth.model.entity.Role;
import org.softuni.maintenancemanager.auth.model.repositories.RolesRepository;
import org.softuni.maintenancemanager.auth.service.interfaces.RoleService;
import org.softuni.maintenancemanager.errorHandling.exceptions.EntryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RolesServiceImpl implements RoleService{
    private RolesRepository rolesRepository;

    @Autowired
    public RolesServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public Set<String> getAllRoles() {
        return StreamSupport.stream(this.rolesRepository.findAll().spliterator(), false)
                .map(role -> role.getRoleName())
                .collect(Collectors.toSet());
    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        roleName = CharacterEscapes.escapeString(roleName);

        Role role = this.rolesRepository.findByRoleName(roleName);

        if(role == null){
            throw new EntryNotFoundException();
        }

        return role;
    }


}
