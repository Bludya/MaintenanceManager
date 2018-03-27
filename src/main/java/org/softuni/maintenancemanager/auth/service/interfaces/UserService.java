package org.softuni.maintenancemanager.auth.service.interfaces;

import org.softuni.maintenancemanager.auth.model.dtos.binding.UserFullModel;
import org.softuni.maintenancemanager.auth.model.dtos.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.FieldError;

import java.util.List;

public interface UserService extends UserDetailsService {
    FieldError register(UserFullModel userDto);

    boolean userExists(String username);

    boolean userExists(String username, String id);

    List<UserViewModel> getAll();

    UserViewModel getById(String id);

    FieldError edit(UserFullModel userDTO, String id);

    FieldError deactivateUser(String id);

    FieldError delete(String id);
}
