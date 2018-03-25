package org.softuni.maintenancemanager.auth.model.dtos.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserLoginModel {
    private static final String EMPTY_USERNAME_MESSAGE = "Username can't be empty";
    private static final String EMPTY_EMAIL_MESSAGE = "Email can't be empty";
    private static final String EMPTY_PASSWORD_MESSAGE = "Password can't be empty";

    @NotNull(message = EMPTY_USERNAME_MESSAGE)
    @NotEmpty(message = EMPTY_USERNAME_MESSAGE)
    private String username;

    @NotNull(message = EMPTY_EMAIL_MESSAGE)
    @NotEmpty(message = EMPTY_EMAIL_MESSAGE)
    private String email;

    @NotNull(message = EMPTY_PASSWORD_MESSAGE)
    @NotEmpty(message = EMPTY_PASSWORD_MESSAGE)
    private String password;

    public UserLoginModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
