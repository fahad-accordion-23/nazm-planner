package nazmplanner.domain.tasks;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * <h2>Task</h2>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public class Task
{
    
    /* MEMBERS */
    
    private final UUID ID;
    private final LocalDateTime creationDate;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime dueDate;
 
    /* CONSTRUCTOR */
    
    public Task(String title, String description, LocalDateTime dueDate) 
    {
        this.ID = UUID.randomUUID();
        this.creationDate = LocalDateTime.now();
        setTitle(title);
        setDescription(description);
        markTodo();
        setDueDate(dueDate);
    }
    
    /**
     * @brief Used for restoring tasks. DO NOT USE OUTSIDE PERSISTENCE!!!
     * 
     * TODO: replace with something safer
     */
    public Task(UUID id, String title, String description, TaskStatus status, LocalDateTime dueDate, LocalDateTime creationDate)
    {
        this.ID = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.creationDate = creationDate;
    }
    
    /* METHODS */

    public UUID getID()
    {
        return ID;
    }
    
    public LocalDateTime getCreationDate()
    {
        return creationDate;
    }
    
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = Objects.requireNonNullElse(title, "New Task");
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public TaskStatus getStatus()
    {
        return status;
    }

    public LocalDateTime getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate)
    {
        this.dueDate = Objects.requireNonNull(dueDate);
    }   
    
    public void markCompleted()
    {
        status = TaskStatus.COMPLETED;
    }
    
    public void markTodo()
    {
        status = TaskStatus.TODO;
    }
    
}
