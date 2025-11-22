package nazmplanner.ui.tasks.contracts;

import java.util.UUID;

/**
 * <h2>TaskMarkerInterface</h2>
 * 
 * <p>The interface those who wish to mark tasks implement </p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public interface TaskMarkerInterface 
{
    void markTaskCompleted(UUID id);
    void markTaskTodo(UUID id);
}