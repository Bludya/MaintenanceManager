package org.softuni.maintenancemanager.auth.service.interfaces;

import org.softuni.maintenancemanager.auth.model.dtos.binding.UserFullModel;
import org.softuni.maintenancemanager.auth.model.dtos.view.UserViewModel;
import org.softuni.maintenancemanager.auth.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    Object register(UserFullModel userDto);

    boolean userExists(String email, String username);

    boolean userExists(String email, String username, String id);

    List<UserViewModel> getAll();

    Set<UserViewModel> getAllByRole(String role);

    UserViewModel getById(String id);

    UserViewModel getByUsername(String username);

    UserViewModel edit(String editor, UserFullModel userDTO, String id);

    UserViewModel deactivateUser(String editor, String id);

    UserViewModel activateUser(String editor, String id);

    void delete(String editor, String id);

    List<UserViewModel> getAllBySearchWordOrderedByActive(String searchWord);

    User getUserByUsername(String username);

    Set<User> getUsersByUsernames(Set<String> usernames);
}
