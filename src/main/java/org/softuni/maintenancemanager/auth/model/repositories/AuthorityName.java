package org.softuni.maintenancemanager.auth.model.repositories;

import org.softuni.maintenancemanager.auth.model.entity.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityName extends CrudRepository<Authority, String> {
    Authority getByAuthority(String authority);
}
