package org.softuni.maintenancemanager.projects.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "systems")
public class System {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "info")
    private String info;

    @ManyToMany(mappedBy = "systems")
    private Set<Project> projects;

    public System() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Set<Project> getProjects() {
        return Collections.unmodifiableSet(this.projects);
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
