package org.softuni.maintenancemanager.auth.model.repositories;

import org.softuni.maintenancemanager.auth.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    List<User> findAllByUsernameContainsOrEmailContainsOrderByIsEnabledAsc(String searchWord, String searchWord2);

    User getByEmail(String email);

    User getByUsername(String username);

    boolean existsByEmailOrUsername(String email, String username);

    boolean existsByUsername(String username);

    boolean existsByEmailOrUsernameAndIdNot(String email, String username, String id);

    @Transactional
    void deleteByUsername(String username);
}
