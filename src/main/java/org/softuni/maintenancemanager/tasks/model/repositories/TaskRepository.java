package org.softuni.maintenancemanager.tasks.model.repositories;

import org.softuni.maintenancemanager.tasks.model.dtos.view.TaskViewModel;
import org.softuni.maintenancemanager.tasks.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "projects", path = "projects", excerptProjection = TaskViewModel.class)
@CrossOrigin(origins = "http://localhost:8080")
public interface TaskRepository extends JpaRepository<Task, Long> {
}
