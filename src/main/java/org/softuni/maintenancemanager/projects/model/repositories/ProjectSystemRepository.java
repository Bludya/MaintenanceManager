package org.softuni.maintenancemanager.projects.model.repositories;

import org.softuni.maintenancemanager.projects.model.dtos.view.ProjectSystemViewModel;
import org.softuni.maintenancemanager.projects.model.entity.ProjectSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "projectSystems", path = "projectSystems", excerptProjection = ProjectSystemViewModel.class)
@CrossOrigin(origins = "http://localhost:8080")
public interface ProjectSystemRepository extends JpaRepository<ProjectSystem, Long> {
}
