package org.softuni.maintenancemanager.tasks.model.repositories;

import org.softuni.maintenancemanager.tasks.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Set<Task> getByCompleted(Boolean completed);

    @Query(value = "\tSELECT * \n" +
            "\t  FROM tasks AS t\n" +
            "LEFT JOIN tasks_users AS tu ON t.id = tu.task_id\n" +
            "LEFT JOIN users AS u ON u.id = tu.user_id\n" +
            "\t WHERE u.username = ?1", nativeQuery = true)
    Set<Task> getForUser(String username);

}
