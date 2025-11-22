package nazmplanner.ui.tasks.contracts;

import java.util.UUID;

/**
 * <h2>TaskSelectorInterface</h2>
 * 
 * <p>The interface implemented by those who handle task selection events.</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
@FunctionalInterface
public interface TaskSelectorInterface
{
    void selectTask(UUID id);
}