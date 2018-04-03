package org.softuni.maintenancemanager.logger.model.dtos.view;

import java.util.Date;

public class LogViewModel {

    private String user;

    private String activity;

    private Date timeOfOccurrence;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getTimeOfOccurrence() {
        return timeOfOccurrence;
    }

    public void setTimeOfOccurrence(Date timeOfOccurrence) {
        this.timeOfOccurrence = timeOfOccurrence;
    }
}
