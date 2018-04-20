package org.softuni.maintenancemanager.projects.model.repositories;

import org.softuni.maintenancemanager.projects.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashSet;

@CrossOrigin(origins = "http://localhost:8080")
public interface ProjectRepository extends JpaRepository<Project, Long> {

    HashSet<Project> getAllByOrderByProjectNameDesc();

    boolean existsByProjectName(String projectName);

    Project getByProjectName(String projectName);

    Long deleteByProjectName(String projectName);

}
