package org.softuni.maintenancemanager.logger.service.repositories;

import org.softuni.maintenancemanager.logger.model.entity.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LogRepository extends CrudRepository<Log, String> {

    Set<Log> getAllByUser(String username);
}
