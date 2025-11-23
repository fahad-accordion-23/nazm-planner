package nazmplanner.domain.tasks;

import java.util.List;

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
}