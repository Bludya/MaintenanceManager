package org.softuni.maintenancemanager.auth.model.dtos.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class RoleFullModel {
    private static final String EMPTY_ROLENAME_MESSAGE = "This role does not exist.";


    @NotNull(message = EMPTY_ROLENAME_MESSAGE)
    @NotEmpty(message = EMPTY_ROLENAME_MESSAGE)
    private String roleName;

    private String comments;

    private Set<String> users;

    private Set<String> authorities;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
