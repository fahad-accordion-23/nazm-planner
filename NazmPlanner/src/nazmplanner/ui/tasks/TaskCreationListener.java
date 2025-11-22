package nazmplanner.ui.tasks;

import java.time.LocalDateTime;

/**
 * <h2>TaskCreationListener</h2>
 * 
 * <p>The interface listeners who want to hear the TaskCreation event must implement</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
@FunctionalInterface
public interface TaskCreationListener 
{
    void taskCreated(String title, String description, LocalDateTime dueDate);
}