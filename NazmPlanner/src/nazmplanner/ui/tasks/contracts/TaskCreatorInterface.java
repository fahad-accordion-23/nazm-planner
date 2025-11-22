package nazmplanner.ui.tasks.contracts;

import java.time.LocalDateTime;

/**
 * <h2>TaskCreatorInterface</h2>
 * 
 * <p>The interface those who wish to create tasks implement </p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
@FunctionalInterface
public interface TaskCreatorInterface 
{
    void addTask(String title, String description, LocalDateTime dueDate);
}