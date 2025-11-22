package nazmplanner.ui.tasks.contracts;

import java.util.UUID;

/**
 * <h2>TaskDeleterInterface</h2>
 * 
 * <p>The interface those who wish to delete tasks implement </p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
@FunctionalInterface
public interface TaskDeleterInterface 
{
    void deleteTask(UUID id);
}