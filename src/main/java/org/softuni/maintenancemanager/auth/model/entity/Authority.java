package org.softuni.maintenancemanager.auth.model.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "authority", unique = true)
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;

    public Authority() {
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
