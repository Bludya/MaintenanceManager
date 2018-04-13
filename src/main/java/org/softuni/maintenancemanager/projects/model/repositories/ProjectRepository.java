package org.softuni.maintenancemanager.projects.model.repositories;

import org.softuni.maintenancemanager.projects.model.dtos.view.ProjectViewModel;
import org.softuni.maintenancemanager.projects.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "projects", path = "projects", excerptProjection = ProjectViewModel.class)
@CrossOrigin(origins = "http://localhost:8080")
public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByProjectName(String projectName);
}
