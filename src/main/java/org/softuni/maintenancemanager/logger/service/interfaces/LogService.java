package org.softuni.maintenancemanager.logger.service.interfaces;

import org.softuni.maintenancemanager.logger.model.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface LogService {

    Page<Log> getAllLogs(Pageable pageable);

    Set<Log> getLogsByUsername(String username);
}
