package org.softuni.maintenancemanager.auth.model.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "comment")
    private String comment;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(
            name = "roles_authorities",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")}
    )
    private Set<Authority> authorities;

    public Role() {
    }

    public Set<Authority> getAuthorities() {
        return Collections.unmodifiableSet(this.authorities);
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public Set<User> getUsers() {
        return Collections.unmodifiableSet(this.users);
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(Authority authority){
        this.authorities.add(authority);
    }
}
