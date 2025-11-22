package nazmplanner.domain.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import nazmplanner.infrastructure.persistence.tasks.JSONTaskRepository;

/**
 * <h2>TaskSystem</h2>
 * 
 * <p>Manages tasks (CRUD) among other things.</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/25
 */
public class TaskSystem
{
    
    private final TaskRepository taskRepository;

    public TaskSystem()
    {
        taskRepository = new JSONTaskRepository("data/tasks/tasks.json");
    }

    public Task addTask(String title, String description, LocalDateTime dueDate)
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
        
        taskRepository.save(newTask);

        return newTask;
    } 

    public List<Task> getAllTasks()
    {
        return Collections.unmodifiableList(taskRepository.findAll());
    }
    
}
