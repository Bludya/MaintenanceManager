package org.softuni.maintenancemanager.logger.model.dtos.view;

import org.softuni.maintenancemanager.logger.model.entity.Log;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = Log.class, name = "logViewModel")
public interface LogViewModel {

    String getUser();

    String getActivity();

    Date getTimeOfOccurrence();

}
