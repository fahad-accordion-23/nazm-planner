package nazmplanner.ui.tasks.contracts;

import java.util.List;
import nazmplanner.domain.tasks.Task;

/**
 * <h2>TaskUpdaterInterface</h2>
 * 
 * <p>The interface those who wish to update tasks must implement</p>
 */
public interface TasksUpdaterInterface
{
    void updateTasks(List<Task> tasks);
}
