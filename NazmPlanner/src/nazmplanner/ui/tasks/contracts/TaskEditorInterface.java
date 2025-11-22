package nazmplanner.ui.tasks.contracts;

import java.util.UUID;

/**
 * <h2>TaskEditorInterface</h2>
 * 
 * <p>The interface those who wish to edit tasks implement</p>
 */
@FunctionalInterface
public interface TaskEditorInterface 
{
    void editTask(UUID id, String newTitle, String newDescription);
}