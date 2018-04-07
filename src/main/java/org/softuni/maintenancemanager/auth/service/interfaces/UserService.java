package org.softuni.maintenancemanager.auth.service.interfaces;

import org.softuni.maintenancemanager.auth.model.dtos.binding.UserFullModel;
import org.softuni.maintenancemanager.auth.model.dtos.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.FieldError;

import java.util.List;

public interface UserService extends UserDetailsService {
    Object register(UserFullModel userDto);

    boolean userExists(String username);

    boolean userExists(String username, String id);

    List<UserViewModel> getAll();

    UserViewModel getById(String id);

    UserViewModel getByUsername(String username);

    FieldError edit(String editor, UserFullModel userDTO, String id);

    boolean deactivateUser(String editor, String id);

    boolean activateUser(String editor, String id);

    boolean delete(String editor, String id);

    List<UserViewModel> getAllBySearchWordOrderedByActive(String searchWord);
}
