package org.softuni.maintenancemanager.logger.service;

import org.softuni.maintenancemanager.logger.model.entity.Log;
import org.softuni.maintenancemanager.logger.service.interfaces.LogService;
import org.softuni.maintenancemanager.logger.service.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class LogServiceImpl implements LogService {

    LogRepository logRepository;

    @Autowired
    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void addLog(String username, String activity) {
        Log log = new Log();
        log.setActivity(activity);
        log.setUser(username);
        log.setTimeOfOccurrence(new Date());

        this.logRepository.save(log);
    }

    @Override
    public Set<Log> getAllLogs() {
        return (Set<Log>)this.logRepository.findAll();
    }

    @Override
    public Set<Log> getLogsByUsername(String username) {
        return this.logRepository.getAllByUser(username);
    }
}
