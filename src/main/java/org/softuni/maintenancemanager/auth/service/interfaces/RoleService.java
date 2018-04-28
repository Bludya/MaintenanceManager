package org.softuni.maintenancemanager.auth.service.interfaces;

import org.softuni.maintenancemanager.auth.model.entity.Role;

import java.util.Set;

public interface RoleService {

    Set<String> getAllRoles();

    Role getRoleByRoleName(String roleName);
}
