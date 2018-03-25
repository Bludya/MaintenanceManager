package org.softuni.maintenancemanager.auth.service.repositories;

import org.softuni.maintenancemanager.auth.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String>{

    User findByUsernameEquals(String name);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, String id);
}
