package org.softuni.maintenancemanager.auth.model.repositories;

import org.softuni.maintenancemanager.auth.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    List<User> findAllByUsernameContainsOrEmailContainsOrderByIsEnabledAsc(String searchWord, String searchWord2);

    @Query(value = "\tSELECT * \n" +
            "\t  FROM users AS u\n" +
            "LEFT JOIN users_roles AS ur ON u.id = ur.user_id\n" +
            "LEFT JOIN roles_authorities AS ra ON ur.role_id = ra.role_id\n" +
            "LEFT JOIN authority AS a ON ra.authority_id = a.id\n" +
            "\t WHERE a.authority LIKE ?1", nativeQuery = true)
    Set<User> findAllByRole(String role);

    User getByEmail(String email);

    User getByUsername(String username);

    boolean existsByEmailOrUsername(String email, String username);

    boolean existsByUsername(String username);

    boolean existsByEmailOrUsernameAndIdNot(String email, String username, String id);

    @Transactional
    void deleteByUsername(String username);

    Set<User> getAllByUsernameIn(Set<String> usernames);
}
