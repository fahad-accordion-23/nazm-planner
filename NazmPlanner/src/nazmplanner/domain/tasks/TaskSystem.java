package nazmplanner.domain.tasks;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

// NEW IMPORTS: Use the SQLite implementation instead of JSON
import nazmplanner.infrastructure.persistence.tasks.DatabaseManager;
import nazmplanner.infrastructure.persistence.tasks.SqliteTaskRepository;

/**
 * <h2>TaskSystem</h2>
 * * <p>Manages tasks (CRUD) among other things.</p>
 * * @author Fahad Hassan
 * @version 22/11/25
 */
public class TaskSystem
{
    
    private final TaskRepository taskRepository;

    public TaskSystem()
    {
        // 1. Initialize the Database (Creates the file and tables if they don't exist)
        DatabaseManager.initializeDatabase();

        // 2. Use the SQLite Repository
        // This replaces: taskRepository = new JSONTaskRepository("data/tasks/tasks.json");
        taskRepository = new SqliteTaskRepository();
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
    
    public void deleteTask(UUID id) {
    	taskRepository.delete(id);
    	
    }
    
    public Task findById(UUID id)
    {
        return taskRepository.findById(id);
    }
    
}