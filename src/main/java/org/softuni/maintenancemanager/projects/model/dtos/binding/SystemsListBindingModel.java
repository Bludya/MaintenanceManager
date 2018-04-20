package org.softuni.maintenancemanager.projects.model.dtos.binding;

import java.util.List;

public class SystemsListBindingModel {

    private List<String> systems;

    public SystemsListBindingModel(List<String> systems) {
        this.systems = systems;
    }

    public List<String> getSystems() {
        return systems;
    }

    public void setSystems(List<String> systems) {
        this.systems = systems;
    }
}
