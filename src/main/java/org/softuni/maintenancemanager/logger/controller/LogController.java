package org.softuni.maintenancemanager.logger.controller;

import org.softuni.maintenancemanager.logger.model.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/logs")
public class LogController {
    LogRepository logRepository;

    @Autowired
    public LogController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @PostMapping("/all")
    public Object allLogs(@RequestParam(required = false) String searchWord){
        return this.logRepository.findAll();
    }
}
