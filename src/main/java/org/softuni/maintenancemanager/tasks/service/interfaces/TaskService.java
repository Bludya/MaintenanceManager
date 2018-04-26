package org.softuni.maintenancemanager.tasks.service.interfaces;

import org.softuni.maintenancemanager.tasks.model.dtos.binding.TaskBindModel;
import org.softuni.maintenancemanager.tasks.model.dtos.view.TaskBasicViewModel;
import org.softuni.maintenancemanager.tasks.model.dtos.view.TaskViewModel;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface TaskService {

    TaskViewModel addTask(String user, TaskBindModel taskDto, Set<String> employees);

    Set<TaskBasicViewModel> getUsersTasks(String username);

    Set<TaskBasicViewModel> getByCompleted(Boolean completed);

    TaskViewModel getTaskById(Long id);

    TaskViewModel finishTask(String name, Long id, String closingNote);

    TaskViewModel addNote(String name, Long id, String note);
}
