package nazmplanner.domain.tasks;

import java.util.List;
import java.util.UUID;

/**
 * <h2>TaskRepository</h2>
 * 
 * <p> Interface for Task persistence. </p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public interface TaskRepository 
{
    void save(Task task);
    List<Task> findAll();
    Task findById(UUID id);
    void delete(UUID id);
    
}