package org.softuni.maintenancemanager.auth.model.dtos.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//used both for edit and register
public class UserFullModel {
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

    @NotNull(message = EMPTY_PASSWORD_MESSAGE)
    @NotEmpty(message = EMPTY_PASSWORD_MESSAGE)
    private String repeatPassword;

    public UserFullModel(@NotNull(message = EMPTY_USERNAME_MESSAGE) @NotEmpty(message = EMPTY_USERNAME_MESSAGE) String username, @NotNull(message = EMPTY_EMAIL_MESSAGE) @NotEmpty(message = EMPTY_EMAIL_MESSAGE) String email, @NotNull(message = EMPTY_PASSWORD_MESSAGE) @NotEmpty(message = EMPTY_PASSWORD_MESSAGE) String password, @NotNull(message = EMPTY_PASSWORD_MESSAGE) @NotEmpty(message = EMPTY_PASSWORD_MESSAGE) String repeatPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public UserFullModel() {
    }

    //private String comments;

    public UserFullModel(String u) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
