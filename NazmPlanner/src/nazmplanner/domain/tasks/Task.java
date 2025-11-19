package nazmplanner.domain.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Task class.
 * 
 * @author Fahad Hassan
 * @version 19/11/2025
 */
public class Task
{
    
    /* MEMBERS */
    
    private final UUID ID;
    private final LocalDateTime creationDate;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
 
    /* CONSTRUCTOR */
    
    public Task(String title, String description, LocalDate dueDate) 
    {
        this.ID = UUID.randomUUID();
        this.creationDate = LocalDateTime.now();
        setTitle(title);
        setDescription(description);
        markTodo();
        setDueDate(dueDate);
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

    public LocalDate getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate)
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
