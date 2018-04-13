package org.softuni.maintenancemanager.logger.model.repositories;

import org.softuni.maintenancemanager.logger.model.dtos.view.LogViewModel;
import org.softuni.maintenancemanager.logger.model.entity.Log;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "logs", path = "logs", excerptProjection = LogViewModel.class)
@CrossOrigin(origins = "http://localhost:8080")
public interface LogRepository extends PagingAndSortingRepository<Log, String> {

    //Page<Log> findAllByOOrderByTimeOfOccurrenceDesc();

    Set<Log> getAllByUser(String username);
}
