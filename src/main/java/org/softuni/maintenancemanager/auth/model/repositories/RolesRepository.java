package org.softuni.maintenancemanager.auth.model.repositories;

import org.softuni.maintenancemanager.auth.model.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends CrudRepository<Role, String> {
    Role findByRoleName(String roleName);
}
