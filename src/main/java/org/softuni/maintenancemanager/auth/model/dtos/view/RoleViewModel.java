package org.softuni.maintenancemanager.auth.model.dtos.view;

import java.util.HashSet;
import java.util.Set;

public class RoleViewModel {

    private String id;

    private String roleName;

    private Set<String> users;

    private String comments;

    private Set<String> authorities;

    public RoleViewModel() {
        this.authorities = new HashSet<>();
        this.users = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
