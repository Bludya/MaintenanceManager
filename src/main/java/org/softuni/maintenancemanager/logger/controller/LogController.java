package org.softuni.maintenancemanager.logger.controller;

import org.softuni.maintenancemanager.logger.model.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/logs")
public class LogController {
    LogRepository logRepository;

    @Autowired
    public LogController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @GetMapping("/all")
    public Object allLogs(
            @PageableDefault()
            @SortDefault(sort = "timeOfOccurrence", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return this.logRepository.findAll(pageable);
    }
}
