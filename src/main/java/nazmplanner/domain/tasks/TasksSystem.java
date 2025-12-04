package nazmplanner.domain.tasks;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import nazmplanner.infrastructure.persistence.tasks.SqliteTaskRepository;

/**
 * <h2>TaskSystem</h2>
 * 
 * <p>Manages tasks (CRUD) among other things.</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/25
 */
public class TasksSystem
{
    
    private final TaskRepository taskRepository;

    public TasksSystem()
    {
        //taskRepository = new JSONTaskRepository("data/tasks/tasks.json");
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
    
    public Task findById(UUID id)
    {
        Task task = taskRepository.findById(id);
        
        if (Objects.isNull(task))
        {
            throw new IllegalArgumentException("Task with ID " + id + " not found!");        
        }
        
        return task;
    }
    
    public void markTaskCompleted(UUID id)
    {
        Task task = taskRepository.findById(id);
        
        if (Objects.isNull(task))
        {
            throw new IllegalArgumentException("Task with ID " + id + " not found!");        
        }
        
        task.markCompleted();
        taskRepository.save(task);        
    }
    
    public void markTaskTodo(UUID id)
    {
        Task task = taskRepository.findById(id);
        
        if (Objects.isNull(task))
        {
            throw new IllegalArgumentException("Task with ID " + id + "not found!");
        }
        
        task.markTodo();
        taskRepository.save(task);
    }
    
    public void deleteTask(UUID id)
    {
        taskRepository.delete(id);
    }
    
    public void updateTask(UUID id, String newTitle, String newDescription)
    {
        Task task = taskRepository.findById(id);
        
        if (Objects.isNull(task))
        {
            throw new IllegalArgumentException("Task with ID " + id + " not found!");
        }
        
        task.setTitle(newTitle);
        task.setDescription(newDescription);
        
        taskRepository.save(task);
    }
    
}
