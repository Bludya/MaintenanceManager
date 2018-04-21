package org.softuni.maintenancemanager.logger.model.repositories;

import org.softuni.maintenancemanager.logger.model.entity.Log;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

public interface LogRepository extends PagingAndSortingRepository<Log, String> {

    Set<Log> getAllByUser(String username);
}
