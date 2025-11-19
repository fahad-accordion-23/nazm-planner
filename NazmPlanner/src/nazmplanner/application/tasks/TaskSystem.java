package nazmplanner.application.tasks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import nazmplanner.domain.tasks.*;

/**
 * TaskSystem
 * 
 * @brief Manages tasks (CRUD) among other things.
 * @author Fahad Hassan
 * @version 19/11/25
 */
public class TaskSystem
{
    private final Map<UUID, Task> taskRepository;

    public TaskSystem()
    {
        taskRepository = new HashMap<UUID, Task>();
    }

    public Task addTask(String title, String description, LocalDate dueDate)
    {
        if (Objects.isNull(title) || title.trim().isEmpty())
        {
            throw new IllegalArgumentException("Task title cannot be null or empty!");
        }

        if (Objects.isNull(dueDate))
        {
            throw new IllegalArgumentException("Task due date cannot be null!");
        }

        Task newTask = new Task(title, description, dueDate);
        taskRepository.put(newTask.getID(), newTask);

        return newTask;
    } 

    public List<Task> getAllTasks()
    {
        return Collections.unmodifiableList(new ArrayList<>(taskRepository.values()));
    }
}
