package org.softuni.maintenancemanager.tasks.model.dtos.view;

import org.softuni.maintenancemanager.tasks.model.entity.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = Task.class, name = "taskViewModel")
public interface TaskViewModel {
    String getInfo();

    Date getDateCreated();

    Date getDeadline();

    @Value("#{target.getProject().getProjectName()}")
    String getProject();

    Boolean getCompleted();

    @Value("#{target.getTicket().getId()}")
    String getTicket();
}
