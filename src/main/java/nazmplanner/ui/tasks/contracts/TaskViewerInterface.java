package nazmplanner.ui.tasks.contracts;

import java.util.UUID;
import nazmplanner.domain.tasks.Task;

/**
 * <h2>TaskViewerInterface</h2>
 * 
 * <p>The interface implemented by a UI component that displays detailed task information.</p>
 */
public interface TaskViewerInterface
{
    void displayTask(Task task);
}