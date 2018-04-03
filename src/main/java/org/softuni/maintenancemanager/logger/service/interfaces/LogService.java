package org.softuni.maintenancemanager.logger.service.interfaces;


import org.softuni.maintenancemanager.logger.model.entity.Log;

import java.util.Set;

public interface LogService {
    void addLog(String username, String activity);

    Set<Log> getAllLogs();

    Set<Log> getLogsByUsername(String username);
}
